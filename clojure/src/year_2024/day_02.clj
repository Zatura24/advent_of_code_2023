(ns year-2024.day-02
  (:require
    [clojure.string :as str]
    [utils :as utils]))

(defn in-order? [xs]
  (or (= xs (sort xs))
      (= xs (sort > xs))))

(defn in-range? [xs]
  (every? #{1 2 3} (mapv (comp abs -) (rest xs) xs)))

(defn part-1 []
  (->> (utils/read-lines)
       (transduce
         (comp
           (map utils/fields)
           (map utils/parse-ints)
           (map (fn [xs]
                  (and
                    (in-order? xs)
                    (in-range? xs))))
           (filter true?))
         conj)
       count))

(comment
  (part-1))
