(ns day1.day1
  (:require
    [clojure.string :as str]))

(defn readInput [] (map #(Integer/parseInt %) (str/split-lines (slurp "src/day1/input.edn"))))

(defn part-1 []
  (->>
    (readInput)
    (partition 2 1)
    (filter #(apply < %))
    (count)))

(defn part-2 []
  (->>
    (readInput)
    (partition 3 1)
    (map #(reduce + %))
    (map #(reduce + %))
    (partition 2 1)
    (filter #(apply < %))
    (count)))

(comment
  (part-1)
  (part-2))
