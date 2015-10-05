(ns jhs-106.core
  (:require [jhs-106.reg :refer :all]
            [clojure.string :refer [trim replace-first]]))

(def not-nil? (comp not nil?))
(def not-empty? (comp not empty?))

(defn capitalize
  [^String streetname]
  "capitalizes only the first letter, leaving other intact."
  (str (clojure.string/upper-case (.substring streetname 0 1)) (.substring streetname 1)))

(defn- replace-last
  [content what with]
  (clojure.string/reverse (replace-first (clojure.string/reverse content)
                                         (re-pattern (clojure.string/reverse what))
                                         (clojure.string/reverse with))))

(defn unabbreviate
  "Unabbreviates a street name"
  [^String streetname]
  (let [unabbreviated (filter not-nil?
                              (into ()
                                    (for [abbr abbreviations]
                                          (if (.endsWith streetname (val abbr))
                                            (replace-last streetname (val abbr) (name (key abbr)))
                                            nil))))]
    (if (empty? unabbreviated)
      streetname
      (last unabbreviated))))

(defn- street-line
  [line]
  (let [line (clojure.string/trim line)]
    (cond (nil? line) nil
          (re-matches allowedCharacters line) (re-matches street line)
          :default nil)))

(defn- street-data
  [parts]
  (when (not-nil? parts)
    (conj {:name (capitalize (unabbreviate (if (< (count parts) 3) (get parts 0) (trim (get parts 1)))))}
                     (if (not-nil? (get parts 2))
                       {:number (.toLowerCase
                                  (str (get parts 2)
                                       (get parts 3)
                                       (if (not-nil? (get parts 4))
                                         (str "-" (get parts 4)))
                                       (if (not-nil? (get parts 5))
                                         (get parts 5))
                                       (if (not-nil? (get parts 6))
                                         (str "/" (get parts 6)))))})
                     (if (and (not-nil? (get parts 2)) (nil? (get parts 4)))
                       {:numberpart (get parts 2)})
                     (if (not-nil? (get parts 3))
                       {:numberpartition (.toLowerCase ^String (get parts 3))})
                     (if (not-nil? (get parts 5))
                       {:numberpartition (.toLowerCase ^String (get parts 5))})
                     (if (not-nil? (get parts 4))
                       {:startnumber (get parts 2)
                        :endnumber (get parts 4)})
                     (if (not-nil? (get parts 6))
                       {:building (get parts 6)})
                     (if (and (not-nil? (get parts 7)) (re-matches (re-pattern (str "(?:[" ALL_LETTERS "]{1})")) (get parts 7)))
                       {:stairway (.toUpperCase ^String (get parts 7))})
                     (if (not-nil? (get parts 8))
                       {:apartment (.toLowerCase (str (get parts 8) (get parts 9)))})
                     (if (not-nil? (get parts 8))
                       {:apartmentnumber (get parts 8)})
                     (if (not-nil? (get parts 9))
                       {:apartmentpartition (.toLowerCase ^String (get parts 9))}))))

(defn- postal-line
  [lines]
  (if (< 1 (count lines))
    (let [line (last lines)]
      (if (not-nil? line)
        (re-matches postal (clojure.string/trim line))))))

(defn- postal-data
  [parts]
  {:postcode (if (not-nil? (get parts 1))
               (get parts 1))
   :postoffice (if (not-nil? (get parts 2))
                 (.toUpperCase ^String (get parts 2)))})

(defn parse
  "Parses input into JHS-106 specified parts."
  [input]
  (let [lines (clojure.string/split input #"[\n,]+")]
    (into {}
          (filter
            #(not-nil? (val %))
            (conj {:street (street-data (street-line (first lines)))}
                  (postal-data (postal-line lines)))))))

(defn simple-parse
  "Parses input into simple parts."
  [input]
  {:street (dissoc ((parse input) :street) :apartmentnumber :apartmentpartition :numberpart :numberpartition :building :endnumber :startnumber)})
