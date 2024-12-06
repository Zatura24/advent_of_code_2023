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
      [[r c] [-1 0]])))

(defn ^:private find-guard-path
  "Return a vector of position and direction pairs or :loop if a loop is detected"
  [grid guard]
  (loop [[position direction :as guard] guard
         seen #{}
         path []]
    (let [seen (conj seen guard)
          path (conj path guard)
          guard' [(move position direction) direction]]
      (if (contains? seen guard')
        :loop
        (case (get-in grid (first guard'))
          \# (recur [position (turn direction)] seen path)
          (\. \^) (recur guard' seen path)
          path)))))

(defn part-1 []
  (let [grid (parse-input)]
    (->> (find-guard-path grid (find-guard grid))
         (into #{} (map first))
         count)))

(defn ^:private detect-loop-fn [grid]
  (fn [guard obstacle]
    (let [grid (assoc-in grid obstacle \#)]
      (if (= :loop (find-guard-path grid guard))
        1
        0))))

(defn part-2 []
  (let [grid (parse-input)
        guard (find-guard grid)
        path (find-guard-path grid guard)
        obstacles (rest path)]
    (->> obstacles
         (into [] (comp (map first) (distinct)))
         (pmap (detect-loop-fn grid) path)
         (apply +))))

(comment
  (part-1)

  (part-2))
