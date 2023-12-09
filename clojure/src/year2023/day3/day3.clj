(ns year2023.day3.day3
  (:require [utils]
            [clojure.string :as str]))

(defn parse-to-symbol-coords [input]
  (->> input
       str/split-lines
       (map-indexed
         (fn [y line]
           (map-indexed
             (fn [x c]
               [[y x] c])
             line)))
       (apply concat)
       (remove #(str/index-of ".1234567890" (second %)))
       set))

(defn parse-to-num-info [input]
  (->> input
       str/split-lines
       (map-indexed
         (fn [row line]
           (loop [m (re-matcher #"\d+" line)
                  res []]
             (if-let [v (re-find m)]
               (recur m (conj res [[row (. m (start)) (. m (end))] v]))
               res))))
       (apply concat)))


(comment
  (let [input (utils/read-input)
        symbol-coords (parse-to-symbol-coords input)
        num-info (parse-to-num-info input)]
    (reduce
      (fn [acc [[y x1 x2] num]]
        (if (some
              (fn [[[j i] _]]
                (and (<= (dec j) y (inc j))
                     (<= (dec x1) i x2)))
              symbol-coords)
          (+ acc (utils/parse-int num))
          acc))
      0
      num-info))

  (let [input (utils/read-input)
        symbol-coords (parse-to-symbol-coords input)
        num-info (parse-to-num-info input)]
    (reduce
      (fn [acc [[j i] sym]]
        (if (not= sym \*)
          acc
          (let [nums (keep
                       (fn [[[y x1 x2] num]]
                         (when (and (<= (dec j) y (inc j))
                                    (<= (dec x1) i x2))
                           (utils/parse-int num)))
                       num-info)]
            (if (= 2 (count nums))
              (+ acc (apply * nums))
              acc))))
      0
      symbol-coords)))
