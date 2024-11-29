(ns year-2023.day9.day9
  (:require [utils]
            [clojure.string :as str]))

(defn differences [coll]
  (map - (next coll) coll))

(defn seq-of-differences [coll]
  ;; include original coll for easier use in later stages
  (loop [seq-of-diff [coll (differences coll)]]
    (let [new-differences (differences (peek seq-of-diff))]
      (if (every? zero? new-differences)
        seq-of-diff
        (recur (conj seq-of-diff new-differences))))))

(defn parse-histories [input]
  (->> input
       str/split-lines
       (map (comp seq-of-differences
                  utils/parse-ints
                  utils/fields))))

(defn next-value [seq-of-differences]
  (transduce (map last) + seq-of-differences))

(defn prev-value [seq-of-differences]
  (->> seq-of-differences
       (map first)
       reverse
       (reduce (fn [a x] (- x a)))))

(defn part-1 []
  (->> (utils/read-input)
       parse-histories
       (map next-value)
       (apply +)))

(defn part-2 []
  (->> (utils/read-input)
       parse-histories
       (map prev-value)
       (apply +)))

(comment
  (part-1)

  (part-2))
