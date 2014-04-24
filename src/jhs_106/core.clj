(ns jhs-106.core
  (:require [jhs-106.reg :refer :all]
            [clojure.string :refer [trim]]))

(def not-nil? (comp not nil?))

(defn parse
  "Parses input into JHS-106 specified parts."
  [input]
  (def parts (re-matches street input))
  {:street (conj {:name (if (< (count parts) 3) (get parts 0) (trim (get parts 1)))}
            (if (not-nil? (get parts 2)) {:number (get parts 2)}))})
