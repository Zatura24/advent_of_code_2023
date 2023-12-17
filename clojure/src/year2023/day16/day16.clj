(ns year2023.day16.day16
  (:require [utils]
            [clojure.string :as str]))

(defn neighbours-fn [grid]
  (fn [acc [r c dr dc]]
    (let [char (get-in grid [r c])]
      (cond
        ;; keep moving in same direction
        (or (= char \.) (and (= char \-) (pos? dc)) (and (= char \|) (pos? dr))) (conj acc [(+ r dr) (+ c dc) dr dc])
        ;; split on rows
        (= char \|) (conj acc [(dec r) c -1 0] [(inc r) c 1 0])
        ;; split on cols
        (= char \-) (conj acc [r (dec c) 0 -1] [r (inc c) 0 1])
        ;;  1  0 ->  0 -1
        ;; -1  0 ->  0  1
        ;;  0  1 -> -1  0
        ;;  0 -1 ->  1  0
        (= char \/) (conj acc [(+ r (* dc -1)) (+ c (* dr -1)) (* dc -1) (* dr -1)])
        ;;  1  0 ->  0  1
        ;; -1  0 ->  0 -1
        ;;  0  1 ->  1  0
        ;;  0 -1 -> -1  0
        (= char \\) (conj acc [(+ r dc) (+ c dr) dc dr])))))

(defn solve [grid start]
  (let [neighbours-fn (neighbours-fn grid)
        w (count grid)
        h (count (first grid))]
    (loop [queue [start]
           visisted #{start}]
      (if (empty? queue)
        (->> visisted (map (fn [[r c _ _]] [r c])) distinct count)
        (let [neighbours (->> queue
                              (reduce neighbours-fn [])
                              (filter (fn [[r c _ _]] (and (< -1 r h) (< -1 c w))))
                              (remove visisted)
                              (into []))]
          (recur neighbours (into visisted neighbours)))))))

(defn part-1 [input] (solve input [0 0 0 1]))

(defn part-2 [input]
  (let [w (count input)
        h (count (first input))
        starts (into [] (concat
                          (for [r (range h)] [r 0 0 1])
                          (for [r (range h)] [r (dec w) 0 -1])
                          (for [c (range w)] [0 c 1 0])
                          (for [c (range w)] [(dec h) c -1 0])))]
    ;; runs sequencially, optimizations possible via clojure.core.reducers
    (transduce (map (partial solve input)) max 0 starts)))

(comment
  (-> (utils/read-input)
      str/split-lines
      ((juxt part-1 part-2))))
