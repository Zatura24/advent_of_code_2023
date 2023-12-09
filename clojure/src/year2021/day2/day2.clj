(ns year2021.day2.day2
  (:require
    [clojure.string :as str]))

(def input (map #(str/split % #" ") (str/split-lines (slurp "src/day2/input.edn"))))

(defn part-1 [pos [direction value]]
  (let [value (Integer/parseInt value)]
    (case direction
      "forward" (update pos :x + value)
      "up" (update pos :y - value)
      "down" (update pos :y + value))))

(defn part-2 [pos [direction value]]
  (let [value (Integer/parseInt value)]
    (case direction
      "forward" (-> pos
                  (update :x + value)
                  (update :y + (* value (:aim pos))))
      "up" (update pos :aim - value)
      "down" (update pos :aim + value))))

(comment
  (apply * (vals (reduce part-1 {:x 0 :y 0} input)))
  (let [{:keys [x y]} (reduce part-2 {:x 0 :y 0 :aim 0} input)]
    (* x y)))
