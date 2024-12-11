(ns year-2024.day-11
  (:require
    [clojure.math :as math]
    [utils :as utils]))

(defn split-number [x n]
  (let [div (math/pow 10 (quot n 2))]
    [(long (quot x div))
     (long (mod x div))]))

(def blinker
  (memoize
    (fn [stone steps]
      (if (zero? steps)
        1
        (if (zero? stone)
          (blinker 1 (dec steps))
          (let [n (count (str stone))
                [l r] (split-number stone n)]
            (if (even? n)
              (+ (blinker l (dec steps))
                 (blinker r (dec steps)))
              (blinker (* stone 2024) (dec steps)))))))))

(defn blink [times]
  (->> (utils/read-input)
       utils/fields
       utils/parse-longs
       (mapv #(blinker % times))
       (apply +)))

(defn part-1 []
  (blink 25))

(defn part-2 []
  (blink 75))


(comment
  (part-1)

  (part-2))
