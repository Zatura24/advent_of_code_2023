(ns year-2024.day-07
  (:require
    [utils :as utils]
    [clojure.string :as str]))

(defn parse-input []
  (->> (utils/read-lines)
       (mapv #(str/split % #": "))
       (mapv (juxt (comp utils/parse-long first)
                   (comp utils/parse-longs utils/fields second)))))

(defn solvable?-fn [allowed-operations]
  (fn solvable? [[result [a b & xs]]]
    (let [operations (mapv #(% a b) allowed-operations)]
      (if (empty? xs)
        (some #(= result %) operations)
        (some #(solvable? [result (cons % xs)]) operations)))))

(defn solve [equations allowed-operations]
  (let [solvable? (solvable?-fn allowed-operations)]
    (transduce
      (comp (filter solvable?)
            (map first))
      +
      equations)))

(defn part-1 []
  (solve (parse-input) #{+ *}))

(defn ^:private concat
  [a b]
  (parse-long (str a b)))

(defn part-2 []
  (solve (parse-input) #{+ * concat}))

(comment
  (part-1)

  (part-2))

