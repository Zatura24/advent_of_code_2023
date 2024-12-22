(ns year-2024.day-18
  (:require
    [clojure.string :as str]
    [grid :as grid]
    [utils :as utils]))

(def SIZE 70)

(defn parse [N]
  (merge
    (into {}
      (for [row (range (inc SIZE))
            col (range (inc SIZE))]
        [[row col] \.]))
    (into {}
          (comp
            (take N)
            (map #(str/split % #","))
            (map utils/parse-ints)
            (map (fn [[c r]] [[r c] \#])))
          (utils/read-lines))))

(defn path [grid]
  (loop [queue (conj (utils/queue) [[0 0] 0])
         seen #{[0 0]}]
    (let [[pos d] (peek queue)
          neighbours (for [dir [grid/UP grid/RIGHT grid/DOWN grid/LEFT]
                           :let [new-pos (grid/move pos dir)]
                           :when (and (= (grid new-pos) \.)
                                      (not (seen new-pos)))]
                       [new-pos (inc d)])]
      (cond
        (empty? queue) :none
        (= pos [SIZE SIZE]) d
        :else (recur (into (pop queue) neighbours)
               (into seen (map first) neighbours))))))

(defn part-1 []
  (path (parse 1024)))

(defn part-2 []
  (let [lines (utils/read-lines)]
    (some
      (fn [N]
        (when (= :none (path (parse N)))
          (nth lines (dec N))))
      (range 1024 (count lines)))))

(comment
  (part-1)

  (part-2))
