(ns year2021.day7.day7
  (:require
    [clojure.string :as str]))

(def input (-> "src/day7/input.edn" (slurp) (str/trim)))

(defn abs [n] (max n (- n)))

(defn distance [a b] (abs (- a b)))

(defn parse-input [input]
  (-> input (str/split #",") (->> (mapv #(Integer/parseInt %)))))

(defn lowest-cost [cost-fn input]
  (reduce
    (fn [lowest index] (min lowest (reduce #(+ %1 (cost-fn (distance index %2))) 0 input)))
    Integer/MAX_VALUE
    (range (apply min input) (inc (apply max input)))))

(defn part-1 [input]
  (->> input (parse-input) (lowest-cost identity)))

(defn part-2 [input]
  (->> input (parse-input) (lowest-cost (memoize #(apply + (range 1 (inc %)))))))

(comment
  (part-1 input)

  (part-2 input))
