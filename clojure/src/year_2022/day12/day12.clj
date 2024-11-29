(ns year-2022.day12.day12
  (:require
   [clojure.string :as str]))

;; Returns {[row col] height ...}
(defn read-input []
  (->>
   (slurp "src/day12/input.edn")
   (str/split-lines)
   (map-indexed
    (fn [row string-row]
      (map-indexed
       (fn [col height]
         [[row col] height])
       string-row)))
   (apply concat)
   (into {})))

;; Also store start and end in heightmap
;; and set their correct height so we do not have to do special operations
(defn find-start-and-end [input puzzle]
  (reduce-kv
   (fn [input key value]
     (case value
       \S (assoc input (if (= puzzle 2) :end :start) key key \a)
       \E (assoc input (if (= puzzle 2) :start :end) key key \z)
       input))
   input
   input))

;; All valid nodes are in the heightmap
(defn neighbours [heightmap visited node puzzle]
  (let [filter-fn (if (= puzzle 2)
                    #(>= (- (int (heightmap %)) (int (heightmap node))) -1)
                    #(<= (- (int (heightmap %)) (int (heightmap node))) 1))]
    (->> [[1 0] [-1 0] [0 1] [0 -1]]
         (reduce #(conj %1 (mapv + node %2)) [])
         (filterv heightmap)
         (filterv (complement visited))
         (filterv filter-fn))))

;; Store [[row col] count] in a queue
(defn solve [{:keys [start] :as heightmap} puzzle]
  (loop [q (conj clojure.lang.PersistentQueue/EMPTY [start 0])
         v #{start}]
    (let [[node count] (peek q)
          neighbours (neighbours heightmap v node puzzle)]
      (if (if (= puzzle 2)
            (= (heightmap node) \a)
            (= node (:end heightmap)))
        count
        (recur
         (into (pop q) (mapv #(vector % (inc count)) neighbours))
         (into v neighbours))))))

(defn part-1 []
  (-> (read-input)
      (find-start-and-end 1)
      (solve 1)))

(defn part-2 []
  (-> (read-input)
      (find-start-and-end 2)
      (solve 2)))

(comment
  (part-1)

  (part-2))
