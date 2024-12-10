(ns year-2024.day-10
  (:require
    [utils :as utils]))

(defn indexed [items]
  (map-indexed vector items))

(defn parse-input []
  (into {}
        (for [[r line] (indexed (utils/read-lines))
              [c v] (indexed line)]
          [[r c] (utils/parse-int v)])))

(defn move [position direction]
  (mapv + position direction))

(defn neighbours-fn [grid]
  (fn [pos]
    (into
      []
      (comp
        (map (partial move pos))
        (filter #(and (get grid pos)
                      (= (grid %) (inc (grid pos))))))
      [[-1 0] [0 1] [1 0] [0 -1]])))

(defn starting-points [grid]
  (keep #(when (= 0 (val %)) (key %)) grid))

(defn reachable-tops [grid start]
  (let [neighbours-fn (neighbours-fn grid)]
    (loop [queue [start]
           seen #{start}
           tops []]
      (if (empty? queue)
        tops
        (let [neighbours (into [] (comp
                                    (mapcat neighbours-fn)
                                    (remove seen)) queue)]
          (recur
            neighbours
            (into seen neighbours)
            (into tops (filter #(= 9 (grid %))) neighbours)))))))

(defn part-1 []
  (let [grid (parse-input)]
    (->> grid
         starting-points
         (map #(reachable-tops grid %))
         (mapcat distinct)
         count)))

(defn part-2 []
  (let [grid (parse-input)]
    (->> grid
         starting-points
         (mapcat #(reachable-tops grid %))
         count)))

(comment
  (part-1)

  (part-2))
