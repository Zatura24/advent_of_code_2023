(ns day12.day12
  (:require
   [clojure.string :as str]))

(defn read-input []
  (->> (slurp "src/day13/example.edn")
       (str/split-lines)
       (remove #{""})
       (mapv read-string)
       (partition-all 2)))

(defn solve 
  ([[p1 p2]] (solve p1 p2))
  ([p1 p2]
   (cond
     (and (integer? p1) (integer? p2)) (- p1 p2)
     (and (integer? p1) (vector? p2)) (solve [p1] p2)
     (and (vector? p1) (integer? p2)) (solve p1 [p2])
     (and (vector? p1) (vector? p2)) (->> (mapv solve p1 p2) 
                                          (filter some?)
                                          (filter (complement zero?)) 
                                          (first))
     :else (- (count p1) (count p2)))))

(comment
  (->> (read-input)
    (map solve)
    #_(map-indexed (fn [i a] (when (and (some? a) (neg? a)) i))))

  (part-1)
  (part-2))
