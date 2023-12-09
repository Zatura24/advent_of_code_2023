(ns year2023.day2.day2
  (:require [utils]
            [clojure.string :as str]))

;; part 1

(def cubes {"red" 12 "green" 13 "blue" 14})

(defn valid-cube? [[_ n c]]
  (>= (get cubes c) (utils/parse-int n)))

(defn part-1 []
  (->> (utils/read-input)
       str/split-lines
       (map (partial re-seq #"(\d+) (red|green|blue)"))
       (keep-indexed
         (fn [idx sets]
           (when (every? valid-cube? sets)
             (inc idx))))
       (apply +)))

;; part 2

(defn parse-draws [sets]
  (reduce
    (fn [a [_ n c]]
      (update a c max (utils/parse-int n)))
    {"red" 0 "green" 0 "blue" 0}
    sets))

(defn part-2 []
  (->> (utils/read-input)
       str/split-lines
       (map (comp (partial apply *)
                  vals
                  parse-draws
                  (partial re-seq #"(\d+) (red|green|blue)")))
       (apply +)))

(comment
  (part-1)

  (part-2))
