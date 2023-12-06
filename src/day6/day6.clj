(ns day6.day6
  (:require [utils]
            [clojure.string :as str]))

(defn ways-to-beat [[time record]]
    (->> (range 1 time)
         (map #(* % (- time %)))
         (filter #(> % record))
         count))

(defn part-1 []
  (->>
    (utils/read-input)
    str/split-lines
    (map (comp utils/parse-ints (partial re-seq #"\d+")))
    ;; zip both lists
    (apply map vector)
    (map ways-to-beat)
    (apply *)))

(defn part-2 []
  (->>
    (utils/read-input)
    str/split-lines
    (map (comp read-string (partial apply str) (partial re-seq #"\d+")))
    vector
    (map ways-to-beat)
    first))

(comment
  (part-1)
  
  (part-2))
