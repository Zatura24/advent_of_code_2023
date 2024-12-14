(ns year-2024.day-12
  (:require
    [utils :as utils]
    [grid :as grid]))

(defn flood [grid pos]
  (loop [queue [pos]
         visited #{pos}]
    (let [neighbours (into []
                           (comp
                             (mapcat grid/neighbours)
                             (distinct)
                             (remove visited)
                             (filter #(= (grid pos) (grid %))))
                           queue)]
      (if (empty? neighbours)
        (into {} (map (fn [k v] [k v]) visited (repeat (grid pos))))
        (recur neighbours (into visited neighbours))))))

(defn region-area [region]
  (count (keys region)))

(defn region-perimeter [region]
  (reduce
    (fn [acc cell]
      (+ acc (- 4 (count (filter region (grid/neighbours cell))))))
    0
    (keys region)))

(defn regions [grid]
  (loop [grid grid
         regions []]
    (let [pos (ffirst grid)]
      (if (nil? pos)
        regions
        (let [region (flood grid pos)]
          (recur
            (apply dissoc grid (keys region))
            (conj regions region)))))))

(defn outside-corner? [grid pos [a _b c]]
  (and (not= (grid pos) (grid a))
       (not= (grid pos) (grid c))))

(defn inside-corner? [grid pos [a b c]]
  (and (= (grid pos) (grid a) (grid c))
       (not= (grid pos) (grid b))))

(defn region-corners [region]
  (reduce
    (fn [acc pos]
      (apply + acc
             (for [corner [grid/TOP-RIGHT grid/BOTTOM-RIGHT grid/BOTTOM-LEFT grid/TOP-LEFT]
                   :let [corner (mapv #(grid/move pos %) corner)]
                   :when (or (outside-corner? region pos corner)
                             (inside-corner? region pos corner))]
               1)))
    0
    (keys region)))

(defn part-1 []
  (let [grid (grid/parse (utils/read-lines))]
    (apply + (map (fn [region] (* (region-area region)
                                  (region-perimeter region))) (regions grid)))))

(defn part-2 []
  (let [grid (grid/parse (utils/read-lines))]
    (apply + (map (fn [region] (* (region-area region)
                                  (region-corners region))) (regions grid)))))

(comment
  (part-1)

  (part-2))

