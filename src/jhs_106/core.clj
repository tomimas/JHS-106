(ns jhs-106.core
  (:require [jhs-106.reg :refer :all]
            [clojure.string :refer [trim replace-first]]))

(def not-nil? (comp not nil?))
(def not-empty? (comp not empty?))

(defn capitalize
  [streetname]
  "capitalizes only the first letter, leaving other intact."
  (str (clojure.string/upper-case (.substring streetname 0 1)) (.substring streetname 1)))

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
  (let [parts (re-matches street input)]
    {:street (conj {:name (capitalize (unabbreviate (if (< (count parts) 3) (get parts 0) (trim (get parts 1)))))}
                   (if (not-nil? (get parts 2))
                     {:number (str (get parts 2)
                                   (get parts 3)
                                   (if (not-nil? (get parts 4))
                                     (str "-" (get parts 4)))
                                   (if (not-nil? (get parts 5))
                                     (str "/" (get parts 5))))})
                   (if (and (not-nil? (get parts 2)) (nil? (get parts 4)))
                     {:numberpart (get parts 2)})
                   (if (not-nil? (get parts 3))
                     {:numberpartition (get parts 3)})
                   (if (not-nil? (get parts 4))
                     {:startnumber (get parts 2)
                      :endnumber (get parts 4)})
                   (if (not-nil? (get parts 5))
                     {:building (get parts 5)})
                   (if (and (not-nil? (get parts 6)) (re-matches (re-pattern (str "(?:[" CAPITAL_LETTERS "]{1})")) (get parts 6)))
                     {:stairway (get parts 6)})
                   (if (not-nil? (get parts 7))
                     {:apartment (str (get parts 7) (get parts 8))})
                   (if (not-nil? (get parts 7))
                     {:apartmentnumber (get parts 7)})
                   (if (not-nil? (get parts 8))
                     {:apartmentpartition (get parts 8)}))}))

(defn simple-parse
  "Parses input into simple parts."
  [input]
  {:street (dissoc ((parse input) :street) :apartmentnumber :apartmentpartition :numberpart :numberpartition :building :endnumber :startnumber)})
