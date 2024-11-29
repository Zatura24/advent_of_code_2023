(ns year-2023.day4.day4
  (:require [utils]
            [clojure.math]
            [clojure.set]
            [clojure.string :as str]))

(defn parse-cards []
  (->>
    (utils/read-input)
    str/split-lines
    (map (comp next (partial re-seq #"\d+|\|")))
    (map (partial utils/split-without #(not= % "|")))
    (map (partial map set))))

(defn part-1 []
  (reduce
    (fn [acc [winning have]]
      (let [overlap (count (clojure.set/intersection winning have))]
        (if (zero? overlap)
          acc
          (+ acc (int (clojure.math/pow 2 (dec overlap)))))))
    0
    (parse-cards)))

(defn part-2 []
  (loop [cards (parse-cards)
         card-counts (into [] (take (count cards) (repeat 1)))
         card-num 0]
    (if-let [[winning have] (first cards)]
      (let [overlap (clojure.set/intersection winning have)]
        (recur
          (rest cards)
          (reduce
            (fn [card-counts next-card-num]
              (update card-counts next-card-num + (get card-counts card-num)))
            card-counts
            (utils/range' (inc card-num) (count overlap)))
          (inc card-num)))
      (apply + card-counts))))

(comment
  (part-1)

  (part-2))

