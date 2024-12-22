(ns year-2024.day-17
  (:require
    [clojure.string :as str]
    [utils :as utils]))

(defn parse []
  (let [[registers program] (utils/split-double-lines (utils/read-input))]
    {:registers (zipmap [:A :B :C] (utils/parse-ints (re-seq #"\d+" registers)))
     :ip 0
     :program (utils/parse-ints (re-seq #"\d" program))
     :out []}))

(defn combo [state operand]
  (case operand
    (0 1 2 3) operand
    4 (get-in state [:registers :A])
    5 (get-in state [:registers :B])
    6 (get-in state [:registers :B])))

(defn adv [state operand]
  (-> state
      (assoc-in [:registers :A] (bit-shift-right (get-in state [:registers :A]) (combo state operand)))
      (update :ip + 2)))

(defn bxl [state operand]
  (-> state
      (assoc-in [:registers :B] (bit-xor (get-in state [:registers :B]) operand))
      (update :ip + 2)))

(defn bst [state operand]
  (-> state
      (assoc-in [:registers :B] (bit-and (combo state operand) 7))
      (update :ip + 2)))

(defn jnz [state operand]
  (if (zero? (get-in state [:registers :A]))
    (update state :ip + 2)
    (assoc state :ip operand)))

(defn bxc [state _operand]
  (-> state
      (assoc-in [:registers :B] (bit-xor (get-in state [:registers :B])
                                         (get-in state [:registers :C])))
      (update :ip + 2)))

(defn out [state operand]
  (-> state
      (update :out conj (mod (combo state operand) 8))
      (update :ip + 2)))

(defn bdv [state operand]
  (-> state
      (assoc-in [:registers :B] (bit-shift-right (get-in state [:registers :A]) (combo state operand)))
      (update :ip + 2)))

(defn cdv [state operand]
  (-> state
      (assoc-in [:registers :C] (bit-shift-right (get-in state [:registers :A]) (combo state operand)))
      (update :ip + 2)))

(defn process [state]
  (let [opcode (nth (:program state) (:ip state))
        operand (nth (:program state) (inc (:ip state)))]
    ((case opcode
       0 adv
       1 bxl
       2 bst
       3 jnz
       4 bxc
       5 out
       6 bdv
       7 cdv)
     state
     operand)))

(defn program [state]
  (loop [state state]
    (if (>= (:ip state) (count (:program state)))
      (str/join "," (:out state))
      (recur (process state)))))

(defn part-1 []
  (program (parse)))

(comment
  (part-1))
