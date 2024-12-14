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

(comment
  (let [robots (parse (utils/read-lines))
        iterations 0]
    (->
      (->> robots
           (mapv
             (fn [[x y dx dy]]
               [(mod (+ x (* iterations dx)) WIDTH)
                (mod (+ y (* iterations dy)) HEIGHT)
                dx dy]))
           (group-by quadrant))
      (dissoc nil)
      vals
      (->> (mapv count)
           (apply *))))


  244 140 625
  244 125 000


  (let [robots (parse (utils/read-lines))]
    (loop [[iterations & rest] (range 10000)
           robots robots
           min-entropy [Integer/MAX_VALUE 0]]
      (if (nil? iterations)
        min-entropy
        (let [new (mapv
                    (fn [[x y dx dy]]
                      [(mod (+ x (* iterations dx)) WIDTH)
                       (mod (+ y (* iterations dy)) HEIGHT)
                       dx dy])
                    robots)
              entropy (-> (group-by quadrant new)
                          (dissoc nil)
                          vals
                          (->> (mapv count) (apply *)))]
          (if (< entropy (first min-entropy))
            (recur rest robots [entropy iterations])
            (recur rest robots min-entropy)))))))
