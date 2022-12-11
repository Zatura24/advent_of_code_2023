(ns day11.day11
  (:require
   [clojure.string :as str]))

(def puzzle (atom 1))

(defn parse-input
  "parses every monkey block into a monkey map
   and calculates a common divisor by multiplying all test values (which are prime)
  
   example result:
   ```{:items [79 98]
       :operation #(* % 19)
       :test 23
       :if-true 2
       :if-false 3
       :inspect-count 0
       :divisor 96577}```"
  [monkeys]
  (let [monkeys (mapv
                 (fn [monkey]
                   (let [[_ starting-items operation test if-true if-false] (str/split-lines monkey)]
                     {:items (mapv #(Integer/parseInt %) (re-seq #"\d+" starting-items))
                      :operation #((case (re-find #"\+|\*" operation) "+" + "*" *)
                                   %
                                   (Integer/parseInt (or (re-find #"\d+" operation) (str %))))
                      :test (Integer/parseInt (re-find #"\d+" test))
                      :if-true (Integer/parseInt (re-find #"\d+" if-true))
                      :if-false (Integer/parseInt (re-find #"\d+" if-false))
                      :inspect-count 0}))
                 monkeys)
        ;; Part 2: https://www.reddit.com/r/adventofcode/comments/zifqmh/comment/izsk4xl
        common-divisor (reduce #(* %1 (:test %2)) 1 monkeys)]
    (mapv #(assoc % :divisor common-divisor) monkeys)))

(defn read-input []
  (->
   (slurp "src/day11/input.edn")
   (str/split #"\n\n")
   (parse-input)))

(defn monkey-turn [monkeys index]
  (reduce
   (fn [monkeys item]
     (let [{:keys [operation test if-true if-false divisor]} (get monkeys index)
           new (if (= 1 @puzzle)
                 (int (/ (operation item) 3))
                 (mod (operation item) divisor))]
       (-> monkeys
           (update-in [(if (zero? (mod new test)) if-true if-false) :items] conj new)
           (update-in [index :items] #(into [] (rest %)))
           (update-in [index :inspect-count] inc))))
   monkeys
   (get-in monkeys [index :items])))

(defn monkey-round [monkeys]
  (reduce
   monkey-turn
   monkeys
   (range (count monkeys))))

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
