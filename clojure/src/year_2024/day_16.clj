(ns year-2024.day-16
  (:require
    [grid :as grid]
    [utils :as utils]))

(defn parse []
  (let [grid (grid/parse (utils/read-lines))]
    {:grid grid
     :start (first (grid/find-all \S grid))
     :facing [0 1]
     :end (first (grid/find-all \E grid))}))

(defn print-grid [grid width height]
  (Thread/sleep 10)
  (dotimes [r width]
    (dotimes [c height]
      (print (grid [r c])))
    (println)))

(defn neighbours [position direction]
  [[(grid/move position direction) direction]
   [(grid/move position (grid/clockwise direction)) (grid/clockwise direction)]
   [(grid/move position (grid/counter-clockwise direction)) (grid/counter-clockwise direction)]])

(defn part-1 []
  (let [{:keys [grid start facing end]} (parse)]
    (loop [queue (conj (sorted-set) [0 start facing])
           visited #{}]
      (let [[score current-pos facing :as head] (first queue)
            queue (disj queue head)]
        (if (= current-pos end)
          score
          (let [visited (conj visited [current-pos facing])
                neighbours (->> (for [[neighbour direction] (neighbours current-pos facing)
                                      :when (and (not (visited [neighbour direction]))
                                                 (not= (grid neighbour) \#)
                                                 (not= direction (mapv - facing)))]
                                  [(if (= direction facing) (inc score) (+ score 1001)) neighbour direction])
                                (into queue))]
            (recur neighbours visited)))))))

#_(defn neighbours2 [score position direction]
  [[(inc score) (grid/move position direction) direction]
   [(+ score 1000) position (grid/clockwise direction)]
   [(+ score 1000) position (grid/counter-clockwise direction)]])


#_(defn trace [paths node]
  (if (empty? (paths node))
    #{}
    (reduce into #{node} (map #(trace paths %) (paths node)))))

#_(defn part-2 []
  (let [{:keys [grid start facing end]} (parse)]
    (println end)
    (loop [queue (conj (sorted-set) [0 start facing])
           visited {}
           previous {}]
      (if (empty? queue)
        (def _previous previous)
        (let [[score current-pos facing :as head] (first queue)
              queue (disj queue head)
              visited (assoc visited [current-pos facing] score)
              neighbours (for [[score neighbour direction] (neighbours2 score current-pos facing)
                               :when (and (<= score (visited [neighbour direction] Integer/MAX_VALUE))
                                          (not= (grid neighbour) \#)
                                          (not= direction (mapv - facing)))]
                           [score neighbour direction])]
          (recur (into queue (filter
                               (fn [[s p d]]
                                 (< s (visited [p d] Integer/MAX_VALUE)))) neighbours)
                 visited
                 (reduce
                   (fn [acc [_ pos dir]]
                     (update acc [pos dir] (fnil conj []) [current-pos facing]))
                   previous
                   neighbours)))))))

(comment
  (part-1))
