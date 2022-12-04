(ns day4.day4
  (:require
    [clojure.string :as str]
    [clojure.edn :as edn]
    [clojure.data]))

(defn read-input [] 
  (->>
    (slurp "src/day4/input.edn")
    (re-seq #"\d+")
    (map #(Integer/parseInt %))
    (partition-all 4)))

(defn overlapping-section-assigments [[start1 end1 start2 end2]]
  (let [assignment1 (into #{} (range start1 (inc end1)))
        assignment2 (into #{} (range start2 (inc end2)))]
    (clojure.data/diff assignment1 assignment2)))

(defn part-1 []
  (->>
    (read-input)
    (map overlapping-section-assigments)
    (filter (fn [[only-in-a only-in-b _]] (or (nil? only-in-a) (nil? only-in-b))))
    count))

(defn part-2 []
  (->>
    (read-input)
    (map overlapping-section-assigments)
    (filter (fn [[_ _ in-both]] (some? in-both)))
    count))
