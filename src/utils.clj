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

(defn split-double-lines
  [^CharSequence s]
  (str/split s #"\r?\n\r?\n"))

(defn sum [coll] (apply + coll))

(defn int [coll] (map #(Integer/parseInt %) coll))
  

