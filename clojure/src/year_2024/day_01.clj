(ns year-2024.day-01
  (:require
   [utils :as utils]))

(defn ^:private parse-input []
  (->> (utils/read-lines)
       (map (comp utils/parse-ints utils/fields))
       (apply map vector)))

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
