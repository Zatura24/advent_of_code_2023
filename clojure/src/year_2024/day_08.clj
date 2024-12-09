(ns year-2024.day-08
  (:require
    [utils :as utils]))

(defn grid-cols [grid]
  (for [r (range (count grid))
        c (range (count (first grid)))]
    [r c]))

(defn parse-grid [grid]
  (reduce
    (fn [acc position]
      (cond-> acc
        (not= (get-in grid position) \.)
        (update (get-in grid position) (fnil conj []) position)))
    {}
    (grid-cols grid)))

(defn pairs [coll]
  (for [i (range (count coll))
        j (range (inc i) (count coll))]
    [(nth coll i) (nth coll j)]))

(defn move [position direction]
  (mapv + position direction))

(defn direction [pos pos']
  (mapv - pos' pos))

(defn part-1 []
  (let [grid (mapv vec (utils/read-lines))]
    (->> (vals (parse-grid grid))
         (mapcat
           (fn [antennas]
             (reduce
               (fn [acc [pos pos']]
                 (let [dir (direction pos pos')]
                   (-> acc
                       (conj (move pos (mapv - dir)))
                       (conj (move pos' dir)))))
               []
               (pairs antennas))))
         (filter #(get-in grid %))
         distinct
         count)))

(defn part-2 []
  (let [grid (mapv vec (utils/read-lines))]
    (->> (vals (parse-grid grid))
         (mapcat
           (fn [antennas]
             (reduce
               (fn [acc [pos pos']]
                 (let [dir (direction pos pos')
                       -dir (mapv - dir)]
                   (-> acc
                       (into (take-while #(get-in grid %) (iterate #(move % -dir) pos)))
                       (into (take-while #(get-in grid %) (iterate #(move % dir) pos'))))))
               []
               (pairs antennas))))
         distinct
         count)))

(comment
  (part-1)

  (part-2))
