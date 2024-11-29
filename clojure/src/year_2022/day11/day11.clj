(ns year-2022.day11.day11
  (:require
   [clojure.string :as str]))

(def puzzle (atom 1))
(def common-divisor (atom 0))

(defn eval-operation [operation]
  (-> (str "(fn [old] (" (re-find #"[\+\*] \w+" operation) " old))")
      (read-string)
      (eval)))

(defn parse-input
  "parses every monkey block into a monkey map
   and calculates a common divisor by multiplying all test values (which are prime)

   example result:
   ```
   {:items [79 98]
    :operation (fn [old] (* 19 old))
    :test 23
    :if-true 2
    :if-false 3
    :inspect-count 0}```"
  [monkeys]
  (let [monkeys (mapv
                 (fn [monkey]
                   (let [[_ starting-items operation test if-true if-false] (str/split-lines monkey)]
                     {:items (mapv #(Integer/parseInt %) (re-seq #"\d+" starting-items))
                      :operation (eval-operation operation)
                      :test (Integer/parseInt (re-find #"\d+" test))
                      :if-true (Integer/parseInt (re-find #"\d+" if-true))
                      :if-false (Integer/parseInt (re-find #"\d+" if-false))
                      :inspect-count 0}))
                 monkeys)]
;; Part 2: https://www.reddit.com/r/adventofcode/comments/zifqmh/comment/izsk4xl
    (reset! common-divisor (reduce #(* %1 (:test %2)) 1 monkeys))
    monkeys))

(defn read-input []
  (->
   (slurp "src/day11/input.edn")
   (str/split #"\n\n")
   (parse-input)))

(defn monkey-turn [monkeys index]
  (let [puzzle1? (= 1 @puzzle)
        divisor @common-divisor
        {:keys [items operation test if-true if-false]} (get monkeys index)]
    (reduce
     (fn [monkeys item]
       (let [new (operation item)
             new (if puzzle1? (int (/ new 3)) (mod new divisor))]
         (update-in
          monkeys
          [(if (zero? (mod new test)) if-true if-false) :items]
          conj
          new)))
     (-> monkeys
         (assoc-in [index :items] [])
         (update-in [index :inspect-count] + (count items)))
     items)))

(defn monkey-round [monkeys]
  (reduce monkey-turn monkeys (range (count monkeys))))

(defn part-1 []
  (reset! puzzle 1)
  (->> (nth (iterate monkey-round (read-input)) 20)
       (map :inspect-count)
       (sort)
       (take-last 2)
       (apply *)))

(defn part-2 []
  (reset! puzzle 2)
  (->> (nth (iterate monkey-round (read-input)) 10000)
       (map :inspect-count)
       (sort)
       (take-last 2)
       (apply *)))

(comment
  (part-1)

  (part-2))
