(ns year-2024.day-02
  (:require
    [utils :as utils]))

(defn- parse-input []
  (mapv (comp utils/parse-ints utils/fields) (utils/read-lines)))

(defn safe? [xs]
  (let [differences (set (mapv - (rest xs) xs))]
    (or (every? #{1 2 3} differences)
        (every? #{-1 -2 -3} differences))))

(defn part-1 []
  (->> (parse-input)
       (filterv safe?)
       count))

(defn part-2 []
  (->> (parse-input)
       (mapv (fn [xs] (map-indexed (fn [i _] (utils/drop-nth i xs)) xs)))
       (filterv #(some safe? %))
       count))

(comment
  (part-1)

  (part-2))
