(ns year-2024.day-03
  (:require [utils]))

(defn ^:private multiply [statement]
  (apply * (utils/parse-ints (re-seq #"\d+" statement))))

(defn part-1 []
  (->> (utils/read-input)
       (re-seq #"mul\(\d{1,3},\d{1,3}\)")
       (transduce (map multiply) +)))

(defn part-2 []
  (->
    (reduce
      (fn [state statement]
        (cond
          (= statement "do()") (assoc state :enabled? true)
          (= statement "don't()") (assoc state :enabled? false)
          (:enabled? state) (assoc state :result (+ (:result state) (multiply statement)))
          :else state))
      {:enabled? true :result 0}
      (re-seq #"mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)" (utils/read-input)))
    :result))

(comment
  (part-1)

  (part-2))
