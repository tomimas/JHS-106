(ns jhs-106.core-test
  (:require [clojure.test :refer :all]
            [jhs-106.core :refer :all]))

(deftest shouldParseStreetBasedOnInput
  (testing "Street name parsing"
    (is (= {:street {:name "Kettulankatu"}} (parse "Kettulankatu")))
    (is (= {:street {:name "Urho Kekkosen katu"}} (parse "Urho Kekkosen katu"))))
  (testing "Special characters in names"
    (is (= {:street {:name "\u00D6rkkitie"}} (parse "\u00D6rkkitie")))
    (is (= {:street {:name "M\u00F6rk\u00F6katu"}} (parse "M\u00F6rk\u00F6katu")))
    (is (= {:street {:name "Tark'ampujankatu"}} (parse "Tark'ampujankatu")))
    (is (= {:street {:name "Gregorius IX:n tie"}} (parse "Gregorius IX:n tie")))
    (is (= {:street {:name "Castr\u00E9nin polku"}} (parse "Castr\u00E9nin polku")))
    (is (= {:street {:name "Vihdintie/Nummela KK"}} (parse "Vihdintie/Nummela KK"))))
  (testing "Street number parsing"
    (is (= {:street {:name "Kettulankatu"
                    :number "2"}} (parse "Kettulankatu 2")))
    (is (= {:street {:name "Kettulankatu"
                    :number "2a"}} (parse "Kettulankatu 2a")))
    (is (= {:street {:name "Kettulankatu"
                    :number "2-4"}} (parse "Kettulankatu 2-4"))))
  (testing "Special characters with street numbers"
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                    :number "1"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1")))
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                    :number "1b"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1b")))
    (is (={ :street {:name "\u00C4mm\u00E4l\u00E4nkatu"
                    :number "1-3"}} (parse "\u00C4mm\u00E4l\u00E4nkatu 1-3")))
    (is (={ :street {:name "Tark'ampujankatu"
                    :number "10"}} (parse "Tark'ampujankatu 10")))
    (is (={ :street {:name "Tark'ampujankatu"
                    :number "10a"}} (parse "Tark'ampujankatu 10a")))
    (is (={ :street {:name "Tark'ampujankatu"
                    :number "10-12"}} (parse "Tark'ampujankatu 10-12")))
    (is (={ :street {:name "Gregorius IX:n tie"
                    :number "12"}} (parse "Gregorius IX:n tie 12")))
    (is (={ :street {:name "Gregorius IX:n tie"
                    :number "12a"}} (parse "Gregorius IX:n tie 12a")))
    (is (={ :street {:name "Gregorius IX:n tie"
                    :number "12-14"}} (parse "Gregorius IX:n tie 12-14")))
    (is (= {:street {:name "Castr\u00E9nin polku"
                     :number "18"}} (parse "Castr\u00E9nin polku 18")))
    (is (= {:street {:name "Vihdintie/Nummela KK"
                     :number "9"}} (parse "Vihdintie/Nummela KK 9")))))