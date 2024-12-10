(ns grid)

(def UP [-1 0])
(def DOWN [1 0])
(def LEFT [0 -1])
(def RIGHT [0 1])

(defn indexed [items]
  (map-indexed vector items))

(defn parse
  ([lines] (parse lines identity))
  ([lines modify-fn]
   (into {}
         (for [[r line] (indexed lines)
               [c v] (indexed line)]
           [[r c] (modify-fn v)]))))

(defn move [position direction]
  (mapv + position direction))
