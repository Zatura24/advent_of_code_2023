(ns year-2024.day-07
  (:require
    [utils :as utils]
    [clojure.math.combinatorics :as combo]
    [clojure.string :as str]))

(defn parse-input []
  (->> (utils/read-lines)
       (mapv #(str/split % #": "))
       (mapv (juxt (comp utils/parse-int first)
                   (comp utils/parse-ints utils/fields second)))))

(defn evaluate [operations numbers]
  (loop [[op & t] operations
         numbers (seq numbers)]
    (if op
      (recur
        t
        (conj (drop 2 numbers)
              (apply op (take 2 numbers))))
      (first numbers))))

(defn solve [equations allowed-operations]
  (transduce
    (comp (filter
            (fn [[result numbers]]
              (let [operations (combo/selections allowed-operations (dec (count numbers)))]
                (some
                  #(= result (evaluate % numbers))
                  operations))))
          (map first))
    +
    equations))

(defn part-1 []
  (solve (parse-input) #{+ *}))

(defn concat [x y]
  (parse-long (str x y)))

(defn part-2 []
  (solve (parse-input) #{+ * concat}))

(comment
  (part-1)

  (part-2))
