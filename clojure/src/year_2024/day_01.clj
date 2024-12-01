(ns year-2024.day-01
  (:require
   [clojure.string :as str]
   [utils :as utils]))

(defn ^:private parse-input []
  (reduce
    (fn [acc line]
      (let [[l r] (str/split line #"\s+")]
        (-> acc
            (update 0 conj (utils/parse-int l))
            (update 1 conj (utils/parse-int r)))))
    [[] []]
    (utils/read-lines)))

(defn part-1 []
  (let [[l r] (mapv sort (parse-input))]
    (reduce + (mapv (comp abs -) l r))))

(defn part-2 []
  (let [[l r] (parse-input)
        frequencies (frequencies r)]
    (transduce (map #(* % (frequencies % 0))) + l)))

(comment
  (part-1)

  (part-2))
