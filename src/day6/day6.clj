(ns day6.day6)

(defn read-input [] (slurp "src/day6/input.edn"))

(defn find-starting-point [size input]
  (loop [[p & rest] (map set (partition-all size 1 input))
         index size]
    (if (= size (count p))
      index
      (recur rest (inc index)))))

(defn part-1 [] (find-starting-point 4 (read-input)))

(defn part-2 [] (find-starting-point 14 (read-input)))
