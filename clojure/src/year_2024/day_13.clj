(ns year-2024.day-13
  (:require
    [utils :as utils]))

;; Each machine is represented by an vector of [xa ya xb yb xp yp]
;;
;; We have to solve (i): xp = A * xa + B * xb,
;;             and (ii): yp = A * ya + B * yb
;;
;; where: A = number of Button A presses,
;;   and: B = number of Button B presses
;;
;; We can get there by subtracting both equations and solving for B:
;;
;; 1. Multiply (i) by (xa / ya):  yp * (xa / ya) = A * ya * (xa / ya) + B * yb * (xa / ya)
;; 2. Cancel out ya in (i):       yp * (xa / ya) = A * xa + B * yb * (xa / ya)
;; 3. Subtract (ii) from (i):     xp - yp * (xa / ya) = A * xa - A * xa + B * xb - B * yb * (xa / ya)
;; 4. Cancel out A * xa:          xp - yp * (xa / ya) = B * xb - B * yb * (xa / ya)
;; 5. Rewrite B terms:            xp - yp * (xa / ya) = B * xb - yb * (xa / ya)
;; 6. Solve for B:                (xp - yp * (xa / ya)) / (xb - yb * (xa / ya)) = B
;;
;; Now we can calculate A by filling in B. We can discard any A and B that are ratio's
;;
;; There are other ways of doing this, but together with my Dad we came up with this solution :)

(defn parse [input]
  (mapv (comp utils/parse-ints #(re-seq #"\d+" %)) (utils/split-double-lines input)))

(defn solve [machines]
  (for [[xa ya xb yb xp yp] machines
        :let [B (/ (- xp (* yp (/ xa ya)))
                   (- xb (* yb (/ xa ya))))
              A (/ (- xp (* xb B)) xa)]
        :when (and (not (ratio? B)) (not (ratio? A)))]
    (+ (* 3 A) B)))

(defn part-1 []
  (->> (utils/read-input)
       parse
       solve
       (apply +)))

(defn part-2 []
  (->> (utils/read-input)
       parse
       (mapv #(-> % (update 4 + 10000000000000) (update 5 + 10000000000000)))
       solve
       (apply +)))

(comment
  (part-1)

  (part-2))
