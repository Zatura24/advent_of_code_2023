(ns year-2024.day-10
  (:require
    [utils :as utils]
    [grid :as grid]))

(defn neighbours-fn [grid]
  (fn [pos]
    (into
      []
      (comp
        (map (partial grid/move pos))
        (filter #(and (get grid pos)
                      (= (grid %) (inc (grid pos))))))
      [grid/UP grid/RIGHT grid/DOWN grid/LEFT])))

(defn starting-points [grid]
  (keep #(when (= 0 (val %)) (key %)) grid))

(defn reachable-tops [grid start]
  (let [neighbours-fn (neighbours-fn grid)]
    (loop [queue [start]]
      ;; does not deduplicate
      ;; meaning the same partial path can be walked twice
      (let [neighbours (into [] (mapcat neighbours-fn) queue)]
        (if (empty? neighbours)
          queue
          (recur neighbours))))))

(defn part-1 []
  (let [grid (grid/parse (utils/read-lines) utils/parse-digit)]
    (->> grid
         starting-points
         (map #(reachable-tops grid %))
         (mapcat distinct)
         count)))

(defn part-2 []
  (let [grid (grid/parse (utils/read-lines) utils/parse-digit)]
    (->> grid
         starting-points
         (mapcat #(reachable-tops grid %))
         count)))

(comment
  (part-1)

  (part-2))
