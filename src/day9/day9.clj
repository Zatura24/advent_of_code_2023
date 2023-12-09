(ns day9.day9
  (:require
   [clojure.string :as str]))

(defn read-input []
  (->> (slurp "src/day9/input.edn")
       (str/split-lines)
       (map #(re-seq #"\d+|\w" %))
       (mapv (fn [[dir times]] [dir (Integer/parseInt times)]))))

(defn signum [n]
  (min (max n -1) 1))

(defn distance [head tail]
  (->> (map - head tail)
       (map abs)
       (reduce max)))

(defn follow [tail head]
  (if (> (distance head tail) 1)
    (->> (map - head tail)
         (map signum)
         (mapv + tail))
    tail))

(defn move-in-direction [knot direction]
  (case direction
    "U" (update knot 1 inc)
    "D" (update knot 1 dec)
    "L" (update knot 0 dec)
    "R" (update knot 0 inc)))

(defn move-rope [{:keys [rope] :as state} direction]
  (let [moved-rope (reduce
                    (fn [moved-rope rope]
                      (conj moved-rope (follow rope (peek moved-rope))))
                    [(move-in-direction (first rope) direction)]
                    (rest rope))]
    (-> state
        (assoc :rope moved-rope)
        (update :visited conj (peek moved-rope)))))

(defn perform-instructions [instructions rope-length]
  (reduce
   (fn [state [dir times]]
     (nth (iterate #(move-rope % dir) state) times))
   {:visited #{[0 0]}
    :rope (repeat rope-length [0 0])}
   instructions))

(defn part-1 []
  (-> (read-input)
      (perform-instructions 2)
      (:visited)
      (count)))

(defn part-2 []
  (-> (read-input)
      (perform-instructions 10)
      (:visited)
      (count)))
