(ns year-2024.day-04
  (:require
    [utils :as utils]))

(defn parse-input []
  (mapv vec (utils/read-lines)))

(defn walk-grid [grid valid-patterns directions]
  (let [rows (count grid)
        cols (count (first grid))]
    (reduce
      (fn [acc r]
        (reduce
          (fn [acc c]
            (reduce
              (fn [acc direction]
                (if (contains? valid-patterns (transduce (map (fn [[dr dc]] (get-in grid [(+ r dr) (+ c dc)]))) str direction))
                  (inc acc)
                  acc))
              acc
              directions))
          acc
          (range cols)))
      0
      (range rows))))

(defn part-1 []
  (let [grid (parse-input)]
    (walk-grid
      grid
      #{"XMAS"}
      ;; All 8 directions from the center (0, 0), starting NW
      [[[0 0] [-1 -1] [-2 -2] [-3 -3]]
       [[0 0] [-1  0] [-2  0] [-3  0]]
       [[0 0] [-1  1] [-2  2] [-3  3]]
       [[0 0] [ 0  1] [ 0  2] [ 0  3]]
       [[0 0] [ 1  1] [ 2  2] [ 3  3]]
       [[0 0] [ 1  0] [ 2  0] [ 3  0]]
       [[0 0] [ 1 -1] [ 2 -2] [ 3 -3]]
       [[0 0] [ 0 -1] [ 0 -2] [ 0 -3]]])))

(defn part-1-alt []
  (let [grid (parse-input)]
    (walk-grid
      grid
      #{"XMAS" "SAMX"}
      ;; 4 directions from the center (0, 0), starting NE
      [[[0 0] [-1  1] [-2  2] [-3  3]]
       [[0 0] [ 0  1] [ 0  2] [ 0  3]]
       [[0 0] [ 1  1] [ 2  2] [ 3  3]]
       [[0 0] [ 1  0] [ 2  0] [ 3  0]]])))


(defn part-2 []
  (let [grid (parse-input)]
    (walk-grid
      grid
      #{"AMSSM" "AMMSS" "ASMMS" "ASSMM"}
      ;; X 'direction' from center going clockwise, starting NW
      [[[0 0] [-1 -1] [-1 1] [1 1] [1 -1]]])))

(comment
  (part-1)

  (part-1-alt)

  (part-2))
