(ns utils
  (:import java.io.File)
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn- parent-ns
  ([] (parent-ns *ns*))
  ([ns]
   (when-let [name (try (-> ns ns-name str) (catch Exception _))]
     (->> (str/split name #"\.")
          butlast
          (map munge)
          (interpose File/separator)
          (apply str)))))

(defn read-input
  ([] (read-input "input.txt"))
  ([file]
   (->
     (parent-ns)
     (str File/separator file)
     io/resource
     slurp)))

(defn read-lines
  ([] (read-lines "input.txt"))
  ([file]
   (with-open [rdr (->
                     (parent-ns)
                     (str File/separator file)
                     io/resource
                     io/reader)]
     (into [] (line-seq rdr)))))

(defn split-double-lines
  [^CharSequence s]
  (str/split s #"\r?\n\r?\n"))

(defn parse-digit [c] (- (int c) (int \0)))

(defn parse-digits [coll] (mapv parse-digit coll))

(defn parse-int [s] (Integer/parseInt (str s)))

(defn parse-ints [coll] (mapv parse-int coll))

(defn parse-long [s] (parse-long (str s)))

(defn parse-longs [coll] (mapv parse-long coll))

(defn split-without [pred coll]
  [(take-while pred coll) (next (drop-while pred coll))])

(defn drop-nth [n coll]
  (concat
    (take n coll)
    (drop (inc n) coll)))

(defn range'
  "Returns a lazy seq with a length from start"
  [start length]
  (take length (drop start (range))))

(defn fields [^CharSequence s]
  (str/split s #"[\t|\n|\v|\f|\r| ]+"))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn queue
  ([] (clojure.lang.PersistentQueue/EMPTY))
  ([coll] (reduce conj clojure.lang.PersistentQueue/EMPTY coll)))
