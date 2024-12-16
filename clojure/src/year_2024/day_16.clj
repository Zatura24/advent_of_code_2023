(ns year-2024.day-16
  (:require
    [grid :as grid]
    [utils :as utils]))

(defn parse []
  (let [grid (grid/parse (utils/read-lines))]
    {:h (count (utils/read-lines))
     :w (count (first (utils/read-lines)))
     :grid grid
     :start (first (grid/find-all \S grid))
     :end (first (grid/find-all \E grid))}))

(defn print-grid [grid width height]
  (Thread/sleep 10)
  (dotimes [r width]
    (dotimes [c height]
      (print (grid [r c])))
    (println)))

(defn part-1 []
  (let [{:keys [grid start end]} (parse)
        facing [0 1]]
    (loop [[[score pos direction] & queue] [[0 start facing]]
           visited #{[start facing]}]
      (if (= pos end)
        score
        (let [visited (conj visited [pos direction])
              neighbours (->> (for [dir [grid/UP grid/RIGHT grid/DOWN grid/LEFT]
                                    :let [neighbour (grid/move pos dir)]
                                    :when (and (not= dir (mapv - direction)) (#{\. \E} (grid neighbour)))]
                                [(if (= dir direction) (inc score) (+ score 1001)) neighbour dir])
                              (remove (fn [[_ neighbour dir]] (visited [neighbour dir])))
                              (into queue)
                              sort
                              (into []))]
          (recur neighbours visited))))))

(comment
  (part-1))
