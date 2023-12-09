(ns year2022.day2.day2
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]))

(defn readInput []
  (mapv #(str/split % #" ") (str/split-lines (slurp "src/day2/input.edn"))))

;; A - rock - X
;; B - paper - Y
;; C - scissor - Z

(defn win-map1 [round]
  (get {["A" "X"] (+ 3 1)
        ["A" "Y"] (+ 6 2)
        ["A" "Z"] (+ 0 3)
        ["B" "X"] (+ 0 1)
        ["B" "Y"] (+ 3 2)
        ["B" "Z"] (+ 6 3)
        ["C" "X"] (+ 6 1)
        ["C" "Y"] (+ 0 2)
        ["C" "Z"] (+ 3 3)}
       round))

(defn part-1 []
  (->>
   (readInput)
   (map win-map1)
   (apply +)))

(defn win-map2 [round]
  (get {["A" "X"] (+ 0 3)
        ["A" "Y"] (+ 3 1)
        ["A" "Z"] (+ 6 2)
        ["B" "X"] (+ 0 1)
        ["B" "Y"] (+ 3 2)
        ["B" "Z"] (+ 6 3)
        ["C" "X"] (+ 0 2)
        ["C" "Y"] (+ 3 3)
        ["C" "Z"] (+ 6 1)} round))

(defn part-2 []
  (->>
   (readInput)
   (map win-map2)
   (apply +)))

(comment
  (part-1)

  (part-2))
