(ns day4.day4
  (:require [utils]
            [clojure.math]
            [clojure.set]
            [clojure.string :as str]))

(comment
  (split-with #(not= % "|") ["a" "|" "b"])

  (clojure.math/pow 2 2)


  (defn calc [x]
    (reduce 
      (fn [acc _] (* acc 2))  
      1
      (next x)))

  (let [input (utils/read-input)
        lines (str/split-lines input)
        cards (map (comp second #(str/split % #"Card\s+\d+: ")) lines)]
    (reduce
      (fn [acc card]
        (let [[winning have] (->> (str/split card #"\|")
                                  (mapv (comp set utils/parse-ints (partial re-seq #"\d+"))))
              overlap (clojure.set/intersection winning have)]
          (+ acc (if (zero? (count overlap)) 
                   0
                   (calc overlap)))))
      0
      cards))

  (->> (utils/read-input)
       str/split-lines
       (map (comp second #(str/split % #"Card \d+: ")))
       (map (comp (partial map (partial re-seq #"\d+"))  #(str/split % #"\|")))
       ))
