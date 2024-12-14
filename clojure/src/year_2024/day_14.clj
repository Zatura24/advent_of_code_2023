(ns year-2024.day-14
  (:require
    [utils :as utils]))

(def WIDTH 101)
(def HEIGHT 103)

(defn parse
  "Represents every robot as [x y dx xy]"
  [input]
  (mapv (comp utils/parse-ints #(re-seq #"-?\d+" %)) input))

(defn quadrant [[x y]]
  (let [mx (/ (dec WIDTH) 2)
        my (/ (dec HEIGHT) 2)]
    (cond
      (and (< x mx) (< y my)) 1
      (and (> x mx) (< y my)) 2
      (and (> x mx) (> y my)) 3
      (and (< x mx) (> y my)) 4)))

(defn safety-factor [robots]
  (-> (group-by quadrant robots)
      (dissoc nil)
      vals
      (->> (mapv count) (apply *))))

(defn view-iteration [iteration robots]
  (mapv
    (fn [[x y dx dy]]
      [(mod (+ x (* iteration dx)) WIDTH)
       (mod (+ y (* iteration dy)) HEIGHT)
       dx dy])
    robots))

(defn part-1 []
  (let [robots (parse (utils/read-lines))]
    (->> robots
         (view-iteration 100)
         safety-factor)))

(defn part-2 []
  (let [robots (parse (utils/read-lines))]
    ;; Cycle occures after width * height iterations
    (loop [[iteration & rest] (range (* WIDTH HEIGHT))
           min-safety-factor [Integer/MAX_VALUE nil]]
      (if (nil? iteration)
        (second min-safety-factor)
        (let [safety-factor (->> robots
                                 (view-iteration iteration)
                                 safety-factor)]
          (if (< safety-factor (first min-safety-factor))
            (recur rest [safety-factor iteration])
            (recur rest min-safety-factor)))))))

(comment
  (part-1)

  (part-2))
