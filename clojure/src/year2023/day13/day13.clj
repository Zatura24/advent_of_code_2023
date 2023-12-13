(ns year2023.day13.day13
  (:require [utils]
            [clojure.string :as str]))

(defn calculate-difference [a b]
    (apply + (map #(if (= %1 %2) 0 1) a b)))

(defn find-split-for-difference [m, d]
  (->>
    (for [i (range 1 (count m))]
      (apply + (mapv calculate-difference (reverse (take i m)) (drop i m))))
    reverse
    (drop-while (partial not= d))
    count))

(defn summarize-notes [d]
  (->> (utils/read-input)
       utils/split-double-lines
       (reduce
         (fn [acc m]
           (let [m (str/split-lines m)]
             ;; vertical | horizontal
             (+ acc
                (-> m utils/transpose (find-split-for-difference d))
                (-> m (find-split-for-difference d) (* 100)))))
         0)))

(def part-1 #(summarize-notes 0))

(def part-2 #(summarize-notes 1))

(comment
  (part-1)

  (part-2))
