(ns year-2024.day-15
  (:require
    [clojure.string :as str]
    [grid :as grid]
    [utils :as utils]))

(def MOVEMENTS {\^ grid/UP \> grid/RIGHT \v grid/DOWN \< grid/LEFT})

(defn parse [input]
  (let [[grid movements] (utils/split-double-lines input)
        grid (grid/parse (str/split-lines grid))]
    {:grid grid
     :movements (mapv MOVEMENTS (str/replace movements #"\n" ""))
     :start (first (grid/find-all \@ grid))}))

(defn parse2 [input]
  (let [[grid movements] (utils/split-double-lines input)
        grid (-> grid
                 (str/replace #"#" "##")
                 (str/replace #"\." "..")
                 (str/replace #"O" "[]")
                 (str/replace #"@" "@.")
                 str/split-lines
                 grid/parse)]
    {:grid grid
     :movements (mapv MOVEMENTS (str/replace movements #"\n" ""))
     :start (first (grid/find-all \@ grid))}))

(defn find-targets [grid start direction]
  (loop [pos start
         targets [start]]
    (let [new-pos (grid/move pos direction)]
      (case (grid new-pos)
        \# :wall
        \. targets
        \O (recur new-pos (conj targets new-pos))))))

(defn find-targets2 [grid start direction]
  (loop [queue [start]
         visisted #{}
         targets [start]]
    (if (empty? queue)
      targets
      (let [visisted (conj visisted (first queue))
            new-pos (grid/move (first queue) direction)]
        (case (grid new-pos)
          \# :wall
          \. (recur (rest queue) visisted targets)
          \[ (let [box (remove visisted [new-pos (grid/move new-pos [0 1])])] ;; ]
               (recur
                 (into (rest queue) box)
                 (into visisted box)
                 (into targets box)))
          \] (let [box (remove visisted [(grid/move new-pos [0 -1]) new-pos])]
               (recur
                 (into (rest queue) box)
                 (into visisted box)
                 (into targets box))))))))

(defn update-grid [grid direction targets]
  (->> targets
       (reduce (fn [new-grid target]
                 (assoc new-grid (grid/move target direction) (grid target)))
               (zipmap targets (repeat \.)))
       (merge grid)))

(defn simulate [find-targets {:keys [grid movements start]}]
  (loop [[direction & movements] movements
         grid grid
         pos start]
    (if (nil? direction)
      grid
      (let [targets (find-targets grid pos direction)]
        (if (= targets :wall)
          (recur movements grid pos)
          (recur movements (update-grid grid direction targets) (grid/move (first targets) direction)))))))

(defn sum [coordinates]
  (->> coordinates
       (mapv (fn [[r c]] (+ (* 100 r) c)))
       (apply +)))

(defn part-1 []
  (->> (utils/read-input)
       parse
       (simulate find-targets)
       (grid/find-all \O)
       sum))

(defn part-2 []
  (->> (utils/read-input)
       parse2
       (simulate find-targets2)
       (grid/find-all \[) ;; ]
       sum))

(comment
  (part-1)

  (part-2))
