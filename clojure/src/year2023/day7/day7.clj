(ns year2023.day7.day7
  (:require [utils]
            [clojure.string :as str]))

;; this mapping allows strings to be sorted
(defn card-mapping [part-2?]
  {"T" (str (char (+ (int \9) 1)))
   "J" (if part-2?
         "1"
         (str (char (+ (int \9) 2))))
   "Q" (str (char (+ (int \9) 3)))
   "K" (str (char (+ (int \9) 4)))
   "A" (str (char (+ (int \9) 5)))})

(defn card-pattern [part-2?]
  (re-pattern (str/join "|" (keys (card-mapping part-2?)))))

(defn sortable-hand [hand part-2?]
  (str/replace hand (card-pattern part-2?) (card-mapping part-2?)))

(defn hand->counter [hand]
  (reduce
    (fn [acc x]
      (update acc x #(inc (or % 0))))
    {}
    hand))

(defn ->part-2-counter [counter]
  (let [j-val (get counter \J)
        new-counter (dissoc counter \J)]
    (if (and j-val (seq new-counter))
      (update new-counter (ffirst (sort-by val > new-counter)) + j-val)
      counter)))

(defn counter->sort-num [counter]
  (case (->> counter vals sort)
    ((5))             7
    ((1, 4))          6
    ((2, 3))          4
    ((1, 1, 3))       3
    ((1, 2, 2))       2
    ((1, 1, 1, 2))    1
    ((1, 1, 1, 1, 1)) 0))

(defn sort-fn [part-2?]
  (fn [[hand _]]
    ;; first sort by type
    ;; then by hand
    ;;
    ;; the counter get's modified by part 2
    [(counter->sort-num (cond-> (hand->counter hand)
                          part-2? ->part-2-counter))
     (sortable-hand hand part-2?)]))

(defn part-1 []
  (->> (utils/read-input)
       str/split-lines
       (map #(str/split % #" "))
       (sort-by (sort-fn false))
       (map (comp utils/parse-int second))
       (map * (iterate inc 1))
       (apply +)))

(defn part-2 []
  (->> (utils/read-input)
       str/split-lines
       (map #(str/split % #" "))
       (sort-by (sort-fn true))
       (map (comp utils/parse-int second))
       (map * (iterate inc 1))
       (apply +)))

(comment
  (part-1)

  (part-2))
