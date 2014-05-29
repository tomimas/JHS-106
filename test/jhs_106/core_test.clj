(ns jhs-106.core-test
  (:require [clojure.test :refer :all]
            [jhs-106.core :refer :all]
            [jhs-106.reg :refer :all]))

(deftest shouldParseStreetBasedInput
  (testing "Street name parsing"
    (is (= {:street {:name "Kettulankatu"}} (parse "Kettulankatu")))
    (is (= {:street {:name "Urho Kekkosen katu"}} (parse "Urho Kekkosen katu"))))
  (testing "Special characters in names"
    (is (= {:street {:name "\u00D6rkkitie"}} (parse "\u00D6rkkitie")))
    (is (= {:street {:name "M\u00F6rk\u00F6katu"}} (parse "M\u00F6rk\u00F6katu")))
    (is (= {:street {:name "Tark'ampujankatu"}} (parse "Tark'ampujankatu")))
    (is (= {:street {:name "Gregorius IX:n tie"}} (parse "Gregorius IX:n tie")))
    (is (= {:street {:name "Castr\u00E9nin polku"}} (parse "Castr\u00E9nin polku")))
    (is (= {:street {:name "Vihdintie/Nummela KK"}} (parse "Vihdintie/Nummela KK")))))

(deftest shouldParseStreetNumberBasedInput
  (testing "Street number parsing"
    (is (= {:street {:name "Kettulankatu"
                     :number "2"
                     :numberpart "2"}} (parse "Kettulankatu 2")))
    (is (= {:street {:name "Kettulankatu"
                     :number "2a"
                     :numberpart "2"
                     :numberpartition "a"}} (parse "Kettulankatu 2a")))
    (is (= {:street {:name "Kettulankatu"
                     :number "2-4"
                     :startnumber "2"
                     :endnumber "4"}} (parse "Kettulankatu 2-4"))))
  (testing "Special characters in street names with street numbers"
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                     :number "1"
                     :numberpart "1"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1")))
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                     :number "1b"
                     :numberpart "1"
                     :numberpartition "b"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1b")))
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                     :number "1-3"
                     :startnumber "1"
                     :endnumber "3"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1-3")))
    (is (={ :street {:name "Tark'ampujankatu"
                     :number "10"
                     :numberpart "10"}} (parse "Tark'ampujankatu 10")))
    (is (={ :street {:name "Tark'ampujankatu"
                     :number "10a"
                     :numberpart "10"
                     :numberpartition "a"}} (parse "Tark'ampujankatu 10a")))
    (is (={ :street {:name "Tark'ampujankatu"
                     :number "10-12"
                     :startnumber "10"
                     :endnumber "12"}} (parse "Tark'ampujankatu 10-12")))
    (is (={ :street {:name "Gregorius IX:n tie"
                     :number "12"
                     :numberpart "12"}} (parse "Gregorius IX:n tie 12")))
    (is (={ :street {:name "Gregorius IX:n tie"
                     :number "12a"
                     :numberpart "12"
                     :numberpartition "a"}} (parse "Gregorius IX:n tie 12a")))
    (is (={ :street {:name "Gregorius IX:n tie"
                     :number "12-14"
                     :startnumber "12"
                     :endnumber "14"}} (parse "Gregorius IX:n tie 12-14")))
    (is (= {:street {:name "Castr\u00E9nin polku"
                     :number "18"
                     :numberpart "18"}} (parse "Castr\u00E9nin polku 18")))
    (is (= {:street {:name "Vihdintie/Nummela KK"
                     :number "9"
                     :numberpart "9"}} (parse "Vihdintie/Nummela KK 9")))))

(deftest shouldParseStairWayBasedInput
  (testing "Simple stairway parsing"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"}} (parse "Kuusikatu 6 A")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"}} (parse "Ulla Tapanisen katu 29 D")))
           (is (= {:street {:name "Ulvilantie"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :stairway "K"}} (parse "Ulvilantie 29/4 K"))))
  (testing "Simple abbreviated streetname parsing"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"}} (parse "Kuusik. 6 A")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"}} (parse "Ulla Tapanisen k. 29 D")))
           (is (= {:street {:name "Ulla Tapanisen raitti"
                            :number "29b"
                            :numberpart "29"
                            :numberpartition "b"
                            :stairway "D"}} (parse "Ulla Tapanisen r. 29b D")))
           (is (= {:street {:name "Ulvilantie"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :stairway "K"}} (parse "Ulvilant. 29/4 K")))))

(deftest shouldParseApartmentBasedInput
  (testing "Simple apartment parsing"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"
                            :apartment "1"
                            :apartmentnumber "1"}} (parse "Kuusikatu 6 A 1")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"
                            :apartment "15"
                            :apartmentnumber "15"}} (parse "Ulla Tapanisen katu 29 D 15")))
           (is (= {:street {:name "Ulvilantie"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :stairway "K"
                            :apartment "825"
                            :apartmentnumber "825"}} (parse "Ulvilantie 29/4 K 825"))))
  (testing "Simple leading zeroes apartment number parsing"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"
                            :apartment "1"
                            :apartmentnumber "1"}} (parse "Kuusikatu 6 A 001")))
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"
                            :apartment "1c"
                            :apartmentnumber "1"
                            :apartmentpartition "c"}} (parse "Kuusikatu 6 A 001c")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"
                            :apartment "15"
                            :apartmentnumber "15"}} (parse "Ulla Tapanisen katu 29 D 015")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"
                            :apartment "15a"
                            :apartmentnumber "15"
                            :apartmentpartition "a"}} (parse "Ulla Tapanisen katu 29 D 015a"))))
  (testing "Abbreviated apartment indicating parsing (it has no stairway)"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :apartment "1"
                            :apartmentnumber "1"}} (parse "Kuusikatu 6 as 1")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :apartment "15"
                            :apartmentnumber "15"}} (parse "Ulla Tapanisen katu 29 as. 15")))
           (is (= {:street {:name "Ulvilav\u00E4gen"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :apartment "825"
                            :apartmentnumber "825"}} (parse "Ulvilav\u00E4gen 29/4 bst 825")))
           (is (= {:street {:name "Ulvilav\u00E4gen"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :apartment "825"
                            :apartmentnumber "825"}} (parse "Ulvilav\u00E4gen 29/4 bst. 825"))))
  (testing "Simple abbreviated streetname parsing"
           (is (= {:street {:name "Kuusikatu"
                            :number "6"
                            :numberpart "6"
                            :stairway "A"
                            :apartment "1"
                            :apartmentnumber "1"}} (parse "Kuusik. 6 A 1")))
           (is (= {:street {:name "Ulla Tapanisen katu"
                            :number "29"
                            :numberpart "29"
                            :stairway "D"
                            :apartment "15"
                            :apartmentnumber "15"}} (parse "Ulla Tapanisen k. 29 D 15")))
           (is (= {:street {:name "Ulla Tapanisen raitti"
                            :number "29b"
                            :numberpart "29"
                            :numberpartition "b"
                            :stairway "D"
                            :apartment "15b"
                            :apartmentnumber "15"
                            :apartmentpartition "b"}} (parse "Ulla Tapanisen r. 29b D 15b")))
           (is (= {:street {:name "Ulvilantie"
                            :number "29/4"
                            :numberpart "29"
                            :building "4"
                            :stairway "K"
                            :apartment "825"
                            :apartmentnumber "825"}} (parse "Ulvilant. 29/4 K 825")))))

(deftest shouldUnAbbreviateStreetName
  (doseq [v abbreviations]
    (is (= (str "Rosvo" (name (key v))) (unabbreviate (str "Rosvo" (val v)))) (str (val v) " => " (name (key v))))
    (is (= (str "Tark'ampujan" (name (key v))) (unabbreviate (str "Tark'ampujan" (val v)))) (str (val v) " => " (name (key v))))
    (is (= (str "\u00C4mm\u00E4l\u00E4n" (name (key v))) (unabbreviate (str "\u00C4mm\u00E4l\u00E4n" (val v)))) (str (val v) " => " (name (key v))))
    (is (= (str "Gregorius IX:n " (name (key v))) (unabbreviate (str "Gregorius IX:n " (val v)))) (str (val v) " => " (name (key v))))))