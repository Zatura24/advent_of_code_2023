(ns year2021.day3.day3
  (:require
    [clojure.string :as str]))

(def input
  (->>
    "src/day3/input.edn"
    (slurp)
    (str/split-lines)
    (map sequence)
    (flatten)
    (map #(if (= \0 %) 0 1))
    (partition 12)))

(defn invert-bits [input size]
  (bit-xor input (dec (int (Math/pow 2 size)))))

(defn most-common-bits [input]
  (->>
    input
    (apply map +)
    (map #(if (<= (/ (count input) 2) %) 1 0))))

(defn part-1 [input]
  (->
    input
    (#(apply str (most-common-bits %)))
    (Integer/parseInt 2)
    (#(* % (invert-bits % (count (first input)))))))

(defn part-2 [input index invert?]
  (if (= 1 (count input))
    (Integer/parseInt (apply str (first input)) 2)
    (let [most-common (cond->
                        (nth
                         (most-common-bits input)
                         index)
                        invert? (invert-bits 1))]
      (part-2 (filter #(= most-common (nth % index)) input) (inc index) invert?))))

(comment
  input
  (part-1 input)
  (* (part-2 input 0 false) (part-2 input 0 true)))
