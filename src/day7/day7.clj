(ns day7.day7
  (:require
    [clojure.string :as str]))

(defn navigation-down? [line]
  (some? (re-matches #"\$ cd \w+" line)))

(defn navigation-up? [line]
  (= "$ cd .." line))

(defn file-size [line]
  (some->> line (re-find #"\d+") (Integer/parseInt)))

(defn ->directory-sizes [lines]
  (loop [[line & remaining] lines
         directories (vector 0)
         done []]
    (cond
      (nil? line) (into done (reverse directories))
      (navigation-down? line) (recur remaining (conj directories 0) done)
      (navigation-up? line) (recur remaining (pop directories) (conj done (peek directories)))
      (file-size line) (recur remaining (mapv #(+ % (file-size line)) directories) done)
      :else (recur remaining directories done))))

(defn read-input [] 
  (->>
    (slurp "src/day7/input.edn")
    str/split-lines
    rest
    ->directory-sizes))

(defn part-1 []
  (->> 
    (read-input)
    (filterv #(<= % 100000))
    (apply +)))

(defn part-2 []
  (let [input (read-input)
        root-size (last input)
        empty-space (- 70000000 root-size)]
    (first (filter #(<= 30000000 (+ empty-space %)) input))))
