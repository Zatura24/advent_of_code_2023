(ns year-2024.day-02
  (:require
    [utils :as utils]))

(defn ^:private parse-input []
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
       (filterv (fn [xs] (some #(safe? (utils/drop-nth % xs)) (range (count xs)))))
       count))

(comment
  (part-1)

  (part-2))
