(ns year-2022.day10.day10
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]
   [clojure.data]))

(defn read-input []
  (->>
   (slurp "src/day10/input.edn")
   (str/split-lines)
   (map #(re-seq #"-\d+|\w+" %))))

;; =======
;;  part1
;; =======

(defn check-interesting-signal [{:keys [cycle x] :as ctx}]
  (if (= (mod (- cycle 20) 40) 0)
    (update ctx :signal + (* cycle x))
    ctx))

(defn perform-instruction [ctx [op param]]
  (case op
    "noop" (-> ctx
               (update :cycle inc)
               (check-interesting-signal))
    "addx" (-> ctx
               (update :cycle inc)
               (check-interesting-signal)
               (update :cycle inc)
               (check-interesting-signal)
               (update :x + (Integer/parseInt param)))))

(defn part-1 []
  (->> (read-input)
       (reduce perform-instruction {:cycle 0 :x 1 :signal 0})
       :signal))

;; =======
;;  part2
;; =======

(defn should-draw? [cursor x]
  (contains?
   (->> [x x x]
        (map + [-1 0 1])
        (into #{}))
   cursor))

(defn draw-pixel [{:keys [cycle x] :as ctx}]
  (let [cursor (mod cycle 40)
        new-line? (= cursor 39)
        should-draw? (should-draw? cursor x)]
    (update ctx :screen str (if should-draw? "#" ".") (when new-line? "\n"))))

(defn perform-instruction2 [ctx [op param]]
  (case op
    "noop" (-> ctx
               (draw-pixel)
               (update :cycle inc))
    "addx" (-> ctx
               (draw-pixel)
               (update :cycle inc)
               (draw-pixel)
               (update :cycle inc)
               (update :x + (Integer/parseInt param)))))

(defn part-2 []
  (->> (read-input)
       (reduce perform-instruction2 {:cycle 0 :x 1 :screen ""})
       :screen
       println))

(comment
  (part-1)

  (part-2))
