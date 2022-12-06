(ns day6.day6
  (:require
    [clojure.string :as str]
    [clojure.edn :as edn]
    [clojure.data]))

(defn read-input [] (str/replace (slurp "src/day6/input.edn") #"\n" ""))

(defn find-starting-point [n partitions]
  (loop [[p & rest] (map #(into #{} %) partitions)
         index 0]
    (when p
      (if (= n (count p))
        (+ index n)
        (recur rest (inc index))))))

(defn part-1 []
  (->> 
    (read-input)
    (partition-all 4 1)
    (find-starting-point 4)))

(defn part-2 []
  (->>
    (read-input)
    (partition-all 14 1)
    (find-starting-point 14)))
