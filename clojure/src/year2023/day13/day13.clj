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

(defn part-1 []
  (->> (utils/read-input)
       utils/split-double-lines
       (reduce
         (fn [acc m]
           (let [m (str/split-lines m)]
             ;; vertical | horizontal
             (+ acc
                (-> m utils/transpose (find-split-for-difference 0))
                (-> m (find-split-for-difference 0) (* 100)))))
         0)))

(defn part-2 []
  (->> (utils/read-input)
       utils/split-double-lines
       (reduce
         (fn [acc m]
           (let [m (str/split-lines m)]
             ;; vertical | horizontal
             (+ acc
                (-> m utils/transpose (find-split-for-difference 1))
                (-> m (find-split-for-difference 1) (* 100)))))
         0)))

(comment
  (part-1)

  (part-2))
