(ns day10.day10
  (:require
    [clojure.string :as str]))

(def input (-> "src/day10/input.txt" (slurp) (str/split-lines)))

(defn parse-input [input]
  (->> input (mapv #(str/split % #""))))

(def legal-characters {"(" ")" "[" "]" "{" "}" "<" ">"})

(defn ->illegal-character-points [characters]
  (mapv #(get {")" 3 "]" 57 "}" 1197 ">" 25137} %) characters))

(defn ->complete-character-points [characters]
  (mapv #(get {")" 1 "]" 2 "}" 3 ">" 4} %) characters))

(defn corrupt-char-or-remaining [line]
  (reduce
    (fn [stack char]
      (if (contains? legal-characters char)
        (conj stack char)
        (if (not= (get legal-characters (peek stack)) char)
          (reduced char)
          (pop stack))))
    '()
    line))

(defn part-1 [input]
  (->> input
    (parse-input)
    (mapv corrupt-char-or-remaining)
    (filter string?)
    (->illegal-character-points)
    (reduce +)))

(defn ->complete-characters [incomplete-characters]
  (mapv #(get legal-characters %) incomplete-characters))

(defn part-2 [input]
  (let [score (->> input
                (parse-input)
                (mapv corrupt-char-or-remaining)
                (filter seq?)
                (mapv (comp
                        (partial reduce #(+ (* 5 %1) %2) 0)
                        ->complete-character-points
                        ->complete-characters))
                (sort)
                (vec))]
    (score (quot (count score) 2))))

(comment
  (part-1 input)
  (part-2 input))
