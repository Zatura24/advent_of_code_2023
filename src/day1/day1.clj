(ns day1.day1
  (:require [utils]
            [clojure.string :as str]))

(defn part-1 []
  (->> (utils/read-input)
       (str/split-lines)
       (map (comp 
              utils/parse-int 
              (partial apply str) 
              (juxt first last)
              (partial re-seq #"\d")))
       (apply +)))

(defn part-2 []
  (let [parse-nums #(get {"one" "1"
                          "two" "2"
                          "three" "3"
                          "four" "4"
                          "five" "5"
                          "six" "6"
                          "seven" "7"
                          "eight" "8"
                          "nine" "9"}
                         %
                         %)]
    (->> (utils/read-input)
         (str/split-lines)
         (map (comp 
                utils/parse-int 
                (partial apply str) 
                (partial map parse-nums)  
                (juxt first last)
                (partial re-seq #"\d|one|two|three|four|five|six|seven|eight|nine")))
         (apply +))))

(comment
  (part-1)
  
  (part-2))
