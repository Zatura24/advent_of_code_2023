(ns day1.day1
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn readInput [] (map #(map edn/read-string (str/split-lines %)) (str/split (slurp "src/day1/input.edn") #"\n\n")))

(defn part-1 []
  (->>
   (readInput)
   (map #(reduce + %))
   (apply max)))

(defn part-2 []
  (->>
   (readInput)
   (map #(reduce + %))
   sort
   (take-last 3)
   (apply +)))
