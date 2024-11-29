(ns year-2022.day8.day8
  (:require
   [clojure.string :as str]))

(defn transpose [m]
  (apply map vector m))

(defn line-length [] (->> (slurp "src/day8/input.edn") (str/split-lines) first count))

(defn read-input []
  (->>
   (slurp "src/day8/input.edn")
   (re-seq #"\d")
   (map #(Integer/parseInt %))
   (partition-all (line-length))))

;; =======
;;  part1
;; ======

(defn trees-visible-in-row?
  "checks every tree in the row if it's visible from the right"
  [row]
  (map-indexed
   (fn [i tree]
     (every?
      #(< % tree)
      (drop (inc i) row)))
   row))

(defn east
  "checks forest from the east"
  [forest]
  (map trees-visible-in-row? forest))

(defn west
  "checks forest from the west"
  [forest]
  (->> forest
       (map reverse)
       (map trees-visible-in-row?)
       (map reverse)))

(defn south
  "checks forest from the south"
  [forest]
  (->> forest
       (transpose)
       (map trees-visible-in-row?)
       (transpose)))

(defn north
  "checks forest from the north"
  [forest]
  (->> forest
       (transpose)
       (map reverse)
       (map trees-visible-in-row?)
       (map reverse)
       (transpose)))

(defn combine-visibility-matrices [& ms]
  (let [[m1 m2 m3 m4] ms]
    (mapcat
     (fn [& rows] (apply map (fn [& cols] (or (some true? cols) false)) rows))
     m1 m2 m3 m4)))

(defn part-1 []
  (let [forest (read-input)
        east   (east forest)
        west   (west forest)
        south  (south forest)
        north  (north forest)]
    (->>
     (combine-visibility-matrices east west south north)
     (filter true?)
     (count))))

;; =======
;;  part2
;; ======

(defn scenic-score-in-row
  "for every tree in the row calculates it's scenic-score"
  [row]
  (println row)
  (map-indexed
   (fn [i tree]
     (filter #(< % tree) row)))
  #_(map-indexed
     (fn [i tree]
       (every?
        #(< % tree)
        (drop (inc i) row)))
     row))

(defn east
  "checks forest from the east"
  [forest]
  (map trees-visible-in-row? forest))

(defn west
  "checks forest from the west"
  [forest]
  (->> forest
       (map reverse)
       (map trees-visible-in-row?)
       (map reverse)))

(defn south
  "checks forest from the south"
  [forest]
  (->> forest
       (transpose)
       (map trees-visible-in-row?)
       (transpose)))

(defn north
  "checks forest from the north"
  [forest]
  (->> forest
       (transpose)
       (map reverse)
       (map trees-visible-in-row?)
       (map reverse)
       (transpose)))

(defn combine-visibility-matrices [& ms]
  (let [[m1 m2 m3 m4] ms]
    (mapcat
     (fn [& rows] (apply map (fn [& cols] (or (some true? cols) false)) rows))
     m1 m2 m3 m4)))

(defn part-2 []
  (let [forest (read-input)
        east   (east forest)
        west   (west forest)
        south  (south forest)
        north  (north forest)]
    (->>
     (combine-visibility-matrices east west south north)
     (filter true?)
     (count))))

(comment
  (part-1)

  (part-2))
