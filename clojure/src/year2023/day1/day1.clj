(ns year2023.day1.day1
  (:require [utils]
            [clojure.string :as str]))

(defn part-1 []
  (->> (utils/read-input)
       str/split-lines
       (map (comp
              utils/parse-int
              (partial apply str)
              (juxt first last)
              (partial re-seq #"\d")))
       (apply +)))

(defn part-2 []
  (let [parse-nums #(get {"one"    "1"
                          "two"    "2"
                          "three"  "3"
                          "four"   "4"
                          "five"   "5"
                          "six"    "6"
                          "seven"  "7"
                          "eight"  "8"
                          "nine"   "9"}
                         %
                         %)
        ;; use positive lookahead to not consume characters
        nums-pattern #"(?=(\d|one|two|three|four|five|six|seven|eight|nine))"]
    (->> (utils/read-input)
         str/split-lines
         (map (comp
                utils/parse-int
                (partial apply str)
                (juxt (comp parse-nums second first)
                      (comp parse-nums second last))
                (partial re-seq nums-pattern)))
         (apply +))))

(defn part-2-alternative []
  (-> (utils/read-input)
      ;; Doing this replacement/insert allows us to reuse part-1's code
      (str/replace
        #"(?=(one|two|three|four|five|six|seven|eight|nine))"
        (comp {"one"    "1"
               "two"    "2"
               "three"  "3"
               "four"   "4"
               "five"   "5"
               "six"    "6"
               "seven"  "7"
               "eight"  "8"
               "nine"   "9"}
              second))
      (->>
        str/split-lines
        (map (comp
               utils/parse-int
               (partial apply str)
               (juxt first last)
               (partial re-seq #"\d")))
        (apply +))))

(comment
  (part-1)

  (part-2)

  (part-2-alternative))
