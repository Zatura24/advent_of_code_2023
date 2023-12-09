(ns year2021.day6.day6
  (:require
    [clojure.string :as str]))

(def input (-> "src/day6/input.edn" (slurp) (str/trim)))

(defn parse-input [input]
  (-> input (str/split #",") (->> (map #(Integer/parseInt %)))))

(defn initial-generation [input]
  (reduce
    (fn [map pos] (assoc map pos (count (filter #{pos} input))))
    {}
    (range 9)))

(defn rotate-left [generation]
  (reduce
    (fn [map pos] (assoc map pos (get generation (inc pos) 0)))
    {}
    (keys generation)))

(defn next-generation [generation]
  (let [first (get generation 0)
        next-generation (rotate-left generation)
        next-generation (update next-generation 6 (partial + first))
        next-generation (assoc next-generation 8 first)]
    next-generation))

(comment
  (apply + (vals (reduce
                   (fn [generation _] (next-generation generation))
                   (initial-generation (parse-input input))
                   (range 256)))))
