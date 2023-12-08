(ns day8.day8
  (:require [utils]
            [clojure.string :as str]))

(defn parse-instructions 
  "Returns a lazy (infinite!) sequence of repetitions of the instructions"
  [instructions]
  (->> instructions (map {\L 0 \R 1}) cycle))

(defn parse-nodes [nodes]
  (->> nodes
       str/split-lines
       (map (partial re-seq #"\w+"))
       (reduce
         (fn [acc [n l r]]
           (assoc acc n [l r]))
         {})))

(defn count-steps 
  "Returns a function takes the starting node and calculates the steps 
   until stop-fn returns true"
  [instructions nodes stop-fn]
  (fn [starting-node]
    (loop [[instruction & xs] (parse-instructions instructions)
           current-node starting-node
           steps 0]
      (if (stop-fn current-node) 
        steps
        (recur xs (get-in nodes [current-node instruction]) (inc steps))))))

(defn part-1 []
  (let [input (utils/read-input)
        [instructions nodes] (str/split input #"\n\n") 
        nodes (parse-nodes nodes)]
    ((count-steps instructions nodes #(= % "ZZZ")) "AAA")))

(defn part-2 []
  (let [input (utils/read-input)
        [instructions nodes] (str/split input #"\n\n")
        nodes (parse-nodes nodes)
        ;; this is a given due to the nature of the puzzle
        ;; see: https://en.wikipedia.org/wiki/Least_common_multiple#Using_the_greatest_common_divisor
        gcd (count instructions)]
    (->> nodes
         keys 
         (filter #(str/starts-with? % "A"))
         (map (count-steps instructions nodes #(str/ends-with? % "Z")))
         (reduce (fn [a x] (/ (* a x) gcd))))))

(comment
  (part-1)

  (part-2))
