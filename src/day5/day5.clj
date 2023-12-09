(ns day5.day5
  (:require
    [clojure.string :as str]))

(def input (-> "src/day5/input.edn" (slurp) (str/split-lines)))

;; Parsing

(defn parse-coordinate [coordinate]
  (-> coordinate (str/split #",") (->> (mapv #(Integer/parseInt %)))))

(defn parse-line [line]
  (-> line (str/split #" -> ") (->> (mapv parse-coordinate))))

(defn parse-input [input]
  (mapv parse-line input))

;; Puzzle

(defn vertical? [[[x1 _] [x2 _]]]
  (= x1 x2))

(defn horizontal? [[[_ y1] [_ y2]]]
  (= y1 y2))

(defn count-overlapping-lines [diagram]
  (->> diagram (vals) (filter #(>= % 2)) (count)))

(defn add-point [diagram point]
  (if (contains? diagram point)
    (update diagram point inc)
    (assoc diagram point 1)))

(defn abs [n] (max n (- n)))

(defn get-points [[[x1 y1] [x2 y2]]]
  (let [dx (- x2 x1)
        dy (- y2 y1)
        maxd (max (abs dx) (abs dy))]
    (for [pos (range (inc maxd))]
      [(+ x1 (* pos (/ dx maxd)))
       (+ y1 (* pos (/ dy maxd)))])))

(defn draw-lines [lines]
  (reduce
    (fn [diagram line]
      (let [points (get-points line)]
        (reduce
          add-point
          diagram
          points)))
    {}
    lines))

(defn part-1 [input]
  (->> input
       (parse-input)
       (filter #(or (vertical? %) (horizontal? %)))
       (draw-lines)
       (count-overlapping-lines)))

(defn part-2 [input]
  (-> input
      (parse-input)
      (draw-lines)
      (count-overlapping-lines)))

(comment
  (part-1 input)
  (part-2 input))
