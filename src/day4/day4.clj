(ns day4.day4
  (:require
    [clojure.string :as str]))

(def input (-> "src/day4/input.edn" (slurp) (str/split #"\n\n" 2)))

;; Parsing

(defn parse-draws [draws]
  (-> draws (str/split #",") (->> (mapv #(Integer/parseInt %)))))

(defn parse-board [board]
  (-> board
    (str/split #"\s+")
    (->> (mapv #(Integer/parseInt %))
      (partition 5)
      (mapv vec))))

(defn parse-boards [boards]
  (-> boards
    (str/split #"\n\n")
    (->> (mapv parse-board))))

(defn parse [[draws boards]]
  {:draws (parse-draws draws)
   :boards (parse-boards boards)})

;; Game

(defn transpose [board]
  (apply mapv vector board))

(defn bingo? [board nums]
  (when
    (or
      (some #(every? nums %) board)
      (some #(every? nums %) (transpose board)))
    board))

(defn board->score [board nums]
  (->> board
    (flatten)
    (remove nums)
    (reduce +)))

(defn part-1 [{:keys [draws boards]}]
  (reduce
    (fn [numbers num]
      (let [numbers (conj numbers num)]
        (if-let [bingo-board (some #(bingo? % numbers) boards)]
          (reduced (* (board->score bingo-board numbers) num))
          numbers)))
    (set (take 4 draws))
    draws))

(defn part-2 [{:keys [draws boards]}]
  (reduce
    (fn [{:keys [numbers boards]} num]
      (let [numbers (conj numbers num)
            last-board (first boards)
            filter-boards (reduce
                            (fn [filter board] (conj filter (bingo? board numbers)))
                            #{}
                            boards)
            boards (remove filter-boards boards)]
        (if (empty? boards)
          (reduced (* (board->score last-board numbers) num))
          {:numbers numbers :boards boards})))
    {:numbers (set (take 4 draws)) :boards boards}
    draws))

(comment
  (part-1 (parse input))
  (part-2 (parse input)))
