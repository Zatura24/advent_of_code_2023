(ns year-2024.day-06
  (:require
    [utils :as utils]))

(defn parse-input []
  (mapv vec (utils/read-lines)))

(defn turn
  "```clojure
  [-1  0] -> [ 0  1]
  [ 0  1] -> [ 1  0]
  [ 1  0] -> [ 0 -1]
  [ 0 -1] -> [-1  0]
  ```"
  [[r c]]
  [c (- r)])

(defn move [position direction]
  (mapv + position direction))

(defn ^:private find-guard [grid]
  (first
    (for [r (range (count grid))
          c (range (count (first grid)))
          :when (= (get-in grid [r c]) \^)]
      [r c])))

(defn ^:private find-guard-path [grid guard]
  (loop [position guard
         direction [-1 0]
         seen #{}]
    (let [seen (conj seen position)
          next-position (move position direction)]
      (case (get-in grid next-position)
        \# (recur (move position (turn direction)) (turn direction) seen)
        (\. \^) (recur next-position direction seen)
        seen))))

(defn part-1 []
  (let [grid (parse-input)]
    (->> grid
         find-guard
         (find-guard-path grid)
         count)))

(defn ^:private detect-loop-fn [grid guard]
  (fn [obstacle]
    (let [grid (assoc-in grid obstacle \#)]
      (loop [position guard
             direction [-1 0]
             seen #{}]
        (let [seen (conj seen [position direction])
              next-position (move position direction)]
          (if (contains? seen [next-position direction])
            1
            (case (get-in grid next-position)
              \# (recur position (turn direction) seen)
              (\. \^) (recur next-position direction seen)
              0)))))))

(defn part-2 []
  (let [grid (parse-input)
        guard (find-guard grid)
        path (find-guard-path grid guard)]
    (->> (disj path guard)
         (pmap (detect-loop-fn grid guard))
         (apply +))))

(comment
  (part-1)

  (part-2))
