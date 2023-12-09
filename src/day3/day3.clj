(ns day3.day3
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn read-input [] (str/split-lines (slurp "src/day3/input.edn")))

(defn item->score [item]
  (let [number (int (first item))]
    (- number (if (>= number (int \a)) 96 38))))

(defn item-in-both-compartments [[compartment1 compartment2]]
  (clojure.set/intersection (set compartment1) (set compartment2)))

(defn part-1 []
  (->>
   (read-input)
   (map #(partition (/ (count %) 2) %))
   (map item-in-both-compartments)
   (map item->score)
   (apply +)))

(defn part-2 []
  (->>
   (read-input)
   (map set)
   (partition-all 3)
   (map #(apply clojure.set/intersection %))
   (map item->score)
   (apply +)))
