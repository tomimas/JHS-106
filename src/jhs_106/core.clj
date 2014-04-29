(ns jhs-106.core
  (:require [jhs-106.reg :refer :all]
            [clojure.string :refer [trim replace-first]]))

(def not-nil? (comp not nil?))

(defn- replace-last
  [content what with]
  (clojure.string/reverse (replace-first (clojure.string/reverse content)
                                         (re-pattern (clojure.string/reverse what))
                                         (clojure.string/reverse with))))

(defn parse
  "Parses input into JHS-106 specified parts."
  [input]
  (def parts (re-matches street input))
  {:street (conj {:name (if (< (count parts) 3) (get parts 0) (trim (get parts 1)))}
            (if (not-nil? (get parts 2)) {:number (get parts 2)}))})

(defn unpabbreviate
  "Unabbreviates a street name"
  [streetname]
  (def unabbreviated nil)
  (doseq [abbr abbreviations]
   (if (and (.endsWith streetname (val abbr)) (nil? unabbreviated))
        (def unabbreviated (replace-last streetname (val abbr) (name (key abbr))))))
  unabbreviated)