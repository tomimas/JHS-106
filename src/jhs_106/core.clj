(ns jhs-106.core
  (:require [jhs-106.reg :refer :all]
            [clojure.string :refer [trim replace-first]]))

(def not-nil? (comp not nil?))
(def not-empty? (comp not empty?))

(defn- replace-last
  [content what with]
  (clojure.string/reverse (replace-first (clojure.string/reverse content)
                                         (re-pattern (clojure.string/reverse what))
                                         (clojure.string/reverse with))))

(defn unabbreviate
  "Unabbreviates a street name"
  [streetname]
  (def unabbreviated nil)
  (doseq [abbr abbreviations]
   (if (and (.endsWith streetname (val abbr)) (nil? unabbreviated))
        (def unabbreviated (replace-last streetname (val abbr) (name (key abbr))))))
  (if (nil? unabbreviated)
    streetname
    unabbreviated))

(defn parse
  "Parses input into JHS-106 specified parts."
  [input]
  (def parts (re-matches street input))
  {:street (conj {:name (unabbreviate (if (< (count parts) 3) (get parts 0) (trim (get parts 1))))}
            (if (not-nil? (get parts 2)) {:number (str (get parts 2) (get parts 3) (get parts 4) (get parts 5))})
            (if (and (not-nil? (get parts 2)) (nil? (get parts 3))) {:numberpart (get parts 2)})
            (if (not-nil? (get parts 3)) {:startnumber (get parts 2)
                                          :endnumber (.substring (get parts 3) 1)})
            (if (not-nil? (get parts 4)) {:building (.substring (get parts 4) 1)})
            (if (not-nil? (get parts 5)) {:numberpartition (get parts 5)})
            (if (and (not-nil? (get parts 6)) (re-matches (re-pattern (str "(?:[" CAPITAL_LETTERS "]{1})")) (get parts 6))) {:stairway (get parts 6)})
            (if (not-nil? (get parts 8)) {:apartment (str (get parts 8) (get parts 9))})
            (if (and (not-nil? (get parts 8)) (not-empty? (get parts 8))) {:apartmentnumber (get parts 8)})
            (if (and (not-nil? (get parts 9)) (not-empty? (get parts 9))) {:apartmentpartition (get parts 9)}))})

(defn simple-parse
  "Parses input into simple parts."
  [input]
  {:street (dissoc ((parse input) :street) :apartmentnumber :apartmentpartition :numberpart :numberpartition :building :endnumber :startnumber)})
