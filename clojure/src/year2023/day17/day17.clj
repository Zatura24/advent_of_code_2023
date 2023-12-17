(ns year2023.day17.day17
  (:require [utils]
            [clojure.string :as str]))

(defn neighbours-fn [grid]
  (let [h (count grid)
        w (count (first grid))]
    (fn [[hl r c dr dc m]]
      (reduce
        (fn [acc [ndr ndc]]
          (let [nr (+ r ndr) nc (+ c ndc)]
            (if (and (< -1 nr h) (< -1 nc w) (not= [ndr ndc] [(* -1 dr) (* -1 dc)]))
              (conj acc [(+ hl (get-in grid [nr nc])) nr nc ndr ndc (if (= [ndr ndc] [dr dc]) (inc m) 1)])
              acc)))
        []
        [[0 1] [1 0] [0 -1] [-1 0]]))))

(defn solve [grid start]
  (let [neighbours-fn (neighbours-fn grid)
        w (count grid)
        h (count (first grid))]
    (loop [[[hl r c dr dc m :as head] & queue] [start]
           ;; don't include heat loss in visisted
           visisted #{[r c dr dc m]}]
      (if (and (>= r (dec h)) (>= c (dec w)))
        hl
        (let [neighbours (->> (neighbours-fn head)
                              (remove (fn [[_ r c dr dc m]] (or (visisted [r c dr dc m]) (> m 3))))
                              (into queue)
                              sort
                              (into []))]
          (recur neighbours (into visisted (map (fn [[_ r c dr dc m]] [r c dr dc m]) neighbours))))))))

(defn part-1 [input] (solve input [0 0 0 0 0 0]))

(comment
  (->> (utils/read-input)
       str/split-lines
      (mapv utils/parse-ints)
      ((juxt part-1))))
