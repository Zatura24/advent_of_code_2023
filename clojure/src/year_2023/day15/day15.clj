(ns year-2023.day15.day15
  (:require [utils]
            [clojure.string :as str]))

(defn hash [s]
  (reduce #(-> %1 (+ (int %2)) (* 17) (mod 256)) 0 s))

(defn part-1 [input]
  (->> input (map hash) (apply +)))

(defn remove-lens [slots step]
  (let [label (re-find #"\w+" step)]
    (into [] (remove (fn [[l _]] (= l label)) slots))))

(defn insert-lens [slots step]
  (let [[label focal-length] (str/split step #"=")
        focal-length (utils/parse-int focal-length)]
    (if-let [i (first (keep-indexed (fn [i [l _]] (when (= l label) i)) slots))]
      (assoc slots i [label focal-length])
      (conj slots [label focal-length]))))

(defn part-2 [input]
  (->> (reduce
         (fn [boxes step]
           (let [step-hash (hash (re-find #"\w+" step))]
             (update boxes step-hash (if (str/includes? step "-") remove-lens insert-lens) step)))
         (into [] (take 256 (repeat [])))
         input)
       (map-indexed (fn [bi slots] (map-indexed (fn [si [_ fl]] (* (+ 1 bi) (inc si) fl)) slots)))
       flatten
       (apply +)))

(comment
  (-> (utils/read-input)
      str/trim
      (str/split #",")
      ((juxt part-1 part-2))))
