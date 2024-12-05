(ns year-2024.day-05
  (:require
    [utils :as utils]
    [clojure.string :as str]))

(defn parse-input []
  (let [data-sets (->> (utils/read-input)
                       str/split-lines
                       (utils/split-without seq))]
    {:rules (into #{} (map utils/fields) (first data-sets))
     :updates (mapv #(str/split % #",") (second data-sets))}))

(defn ^:private pairs [coll]
  (for [i (range (count coll))
        j (range (inc i) (count coll))]
    ;; note j and i are swapped
    [(nth coll j) (nth coll i)]))

(defn ^:private middle-value [coll]
  (utils/parse-int (nth coll (quot (count coll) 2))))

(defn part-1 []
  (let [{:keys [rules updates]} (parse-input)]
    (reduce
      (fn [acc update]
        (if (every? (complement rules) (pairs update))
          (+ acc (middle-value update))
          acc))
      0
      updates)))

(defn part-2 []
  (let [{:keys [rules updates]} (parse-input)]
    (letfn [(sort-fn [i j]
              (contains? rules [i j]))]
      (reduce
        (fn [acc update]
          (if (some rules (pairs update))
            (+ acc (middle-value (sort sort-fn update)))
            acc))
        0
        updates))))

(comment
  (part-1)

  (part-2))
