(ns day5.day5
  (:require
   [clojure.string :as str]
   [clojure.edn :as edn]
   [clojure.data]))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn read-stacks [stack-drawing]
  (->> stack-drawing
       str/split-lines
       drop-last
       transpose
       (map #(remove #{\space \[ \]} %))
       (remove empty?)
       vec))

(defn read-steps [steps-block]
  (->> steps-block
       (re-seq #"\d+")
       (map #(Integer/parseInt %))
       (partition-all 3)
       vec))

(defn parse-input [[stack-drawing steps]]
  {:stacks (read-stacks stack-drawing)
   :steps (read-steps steps)})

(defn read-input []
  (->
   (slurp "src/day5/input.edn")
   (str/split #"\n\n")
   parse-input))

(defn perform-step [{:keys [stacks cratemover-9001?] :as ctx} [amount from to]]
  (let [from (dec from)
        to (dec to)
        [to-move remains] (split-at amount (stacks from))]
    (-> ctx
        (assoc-in [:stacks from] remains)
        (assoc-in [:stacks to]
                  (if cratemover-9001?
                    (concat to-move (stacks to))
                    (apply conj (stacks to) to-move))))))

(defn perform-steps [{:keys [stacks steps] :as ctx}]
  (reduce perform-step ctx steps))

(defn part-1 []
  (->> (read-input)
       perform-steps
       :stacks
       (map first)
       (apply str)))

(defn part-2 []
  (->> (assoc (read-input) :cratemover-9001? true)
       perform-steps
       :stacks
       (map first)
       (apply str)))
