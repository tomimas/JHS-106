(ns jhs-106.reg_test
  (:require [clojure.test :refer :all]
            [jhs-106.reg :refer :all]))

(deftest shouldMatchStreetName
  (testing "Street name regexp matching"
    (is (= "Rosvokatu" (re-matches streetName "Rosvokatu"))))
  (testing "Multipart street names"
    (is (= "Urhean Rosvon katu" (re-matches streetName "Urhean Rosvon katu"))))
  (testing "Special characters"
    (is (= "Tark'ampujankatu" (re-matches streetName "Tark'ampujankatu")))
    (is (= "Tark\u00B4ampujankatu" (re-matches streetName "Tark\u00B4ampujankatu")))
    (is (= "Tark\u00B4ampujank." (re-matches streetName "Tark\u00B4ampujank.")))
    (is (= "Gregorius X:n tie" (re-matches streetName "Gregorius X:n tie")))
    (is (= "Castr\u00E9nin polku" (re-matches streetName "Castr\u00E9nin polku")))
    (is (= "CASTR\u00C9NIN AUKIO" (re-matches streetName "CASTR\u00C9NIN AUKIO")))
    (is (= "\u00D6rkkim\u00F6rkin tie" (re-matches streetName "\u00D6rkkim\u00F6rkin tie")))
    (is (= "\u00C4mm\u00E4l\u00E4nkatu" (re-matches streetName "\u00C4mm\u00E4l\u00E4nkatu")))
    (is (= "Minna \u00C4kkijyrk\u00E4n penger" (re-matches streetName "Minna \u00C4kkijyrk\u00E4n penger")))
    (is (= "Minna \u00C4kkijyrk\u00E4n pgr." (re-matches streetName "Minna \u00C4kkijyrk\u00E4n pgr.")))
    (is (= "\u00C5lans V\u00E4nstra v\u00E5ning" (re-matches streetName "\u00C5lans V\u00E4nstra v\u00E5ning")))
    (is (= "\u00C5lans V. v\u00E5n." (re-matches streetName "\u00C5lans V. v\u00E5n.")))))

(deftest shouldMatchAbbreviatedStreetName
  (testing "Abbreviated street name matching"
           (doseq [v (vals abbreviations)]
             (is (= (str "Rosvo" v) (re-matches streetName (str "Rosvo" v)))))))

(deftest shouldMatchAbbreviatedMultipartStreetName
  (testing "Abbreviated street name matching"
           (doseq [v (vals abbreviations)]
             (is (= (str "Iso Rosvon " v) (re-matches streetName (str "Iso Rosvon " v)))))))

(deftest shouldMatchStreetNumber
  (testing "Street number regexp matching"
    (is (= ["1" "1" nil nil nil] (re-matches streetNumber "1")))
    (is (= ["1z" "1" nil nil "z"] (re-matches streetNumber "1z")))
    (is (= nil (re-matches streetNumber "1Z")))
    (is (= nil (re-matches streetNumber "a")))
    (is (= nil (re-matches streetNumber "B")))
    (is (= ["1-3" "1" "-3" nil nil] (re-matches streetNumber "1-3")))))

(deftest shouldMatchFullStreet
  (testing "full street"
    (is (= ["Rosvokatu 1" "Rosvokatu " "1" nil nil nil nil nil nil nil] (re-matches street "Rosvokatu 1")))
    (is (= ["Rosvokatu 1c" "Rosvokatu " "1" nil nil "c" nil nil nil nil] (re-matches street "Rosvokatu 1c")))
    (is (= ["Rosvokatu 21-23" "Rosvokatu " "21" "-23" nil nil nil  nil nil nil] (re-matches street "Rosvokatu 21-23")))
    (is (= ["Urhean Rosvon katu 1" "Urhean Rosvon katu " "1" nil nil nil nil nil nil nil] (re-matches street "Urhean Rosvon katu 1")))
    (is (= ["Urhean Rosvon katu 1d" "Urhean Rosvon katu " "1" nil nil "d" nil nil nil nil] (re-matches street "Urhean Rosvon katu 1d")))
    (is (= ["Urhean Rosvon katu 4-6" "Urhean Rosvon katu " "4" "-6" nil nil nil nil nil nil] (re-matches street "Urhean Rosvon katu 4-6"))))
  (testing "full street with stairway"
    (is (= ["Rosvokatu 1 B" "Rosvokatu " "1" nil nil nil "B" nil nil nil] (re-matches street "Rosvokatu 1 B"))))
  (testing "full street with apartment"
    (is (= ["Rosvokatu 1 B 1" "Rosvokatu " "1" nil nil nil "B" "" "1" ""] (re-matches street "Rosvokatu 1 B 1")))
    (is (= ["Rosvokatu 1 as 1" "Rosvokatu " "1" nil nil nil "as" "" "1" ""] (re-matches street "Rosvokatu 1 as 1")))
    (is (= ["Rosvokatu 1 as. 1" "Rosvokatu " "1" nil nil nil "as." "" "1" ""] (re-matches street "Rosvokatu 1 as. 1")))
    (is (= ["Rosvokatu 1 bst 1" "Rosvokatu " "1" nil nil nil "bst" "" "1" ""] (re-matches street "Rosvokatu 1 bst 1")))
    (is (= ["Rosvokatu 1 bst. 1" "Rosvokatu " "1" nil nil nil "bst." "" "1" ""] (re-matches street "Rosvokatu 1 bst. 1")))
    (is (= ["Rosvokatu 1 B 1c" "Rosvokatu " "1" nil nil nil "B" "" "1" "c"] (re-matches street "Rosvokatu 1 B 1c")))
    (is (= ["Rosvokatu 1 as 1c" "Rosvokatu " "1" nil nil nil "as" "" "1" "c"] (re-matches street "Rosvokatu 1 as 1c")))
    (is (= ["Rosvokatu 1 as. 1c" "Rosvokatu " "1" nil nil nil "as." "" "1" "c"] (re-matches street "Rosvokatu 1 as. 1c")))
    (is (= ["Rosvokatu 1 bst 1c" "Rosvokatu " "1" nil nil nil "bst" "" "1" "c"] (re-matches street "Rosvokatu 1 bst 1c")))
    (is (= ["Rosvokatu 1 bst. 1c" "Rosvokatu " "1" nil nil nil "bst." "" "1" "c"] (re-matches street "Rosvokatu 1 bst. 1c"))))
  (testing "full street with leading zeroes in apartment number"
    (is (= ["Rosvokatu 1 B 001" "Rosvokatu " "1" nil nil nil "B" "00" "1" ""] (re-matches street "Rosvokatu 1 B 001")))
    (is (= ["Rosvokatu 1 as 001" "Rosvokatu " "1" nil nil nil "as" "00" "1" ""] (re-matches street "Rosvokatu 1 as 001")))
    (is (= ["Rosvokatu 1 as. 001" "Rosvokatu " "1" nil nil nil "as." "00" "1" ""] (re-matches street "Rosvokatu 1 as. 001")))
    (is (= ["Rosvokatu 1 bst 001" "Rosvokatu " "1" nil nil nil "bst" "00" "1" ""] (re-matches street "Rosvokatu 1 bst 001")))
    (is (= ["Rosvokatu 1 bst. 001" "Rosvokatu " "1" nil nil nil "bst." "00" "1" ""] (re-matches street "Rosvokatu 1 bst. 001")))
    (is (= ["Rosvokatu 1 B 001c" "Rosvokatu " "1" nil nil nil "B" "00" "1" "c"] (re-matches street "Rosvokatu 1 B 001c")))
    (is (= ["Rosvokatu 1 as 001c" "Rosvokatu " "1" nil nil nil "as" "00" "1" "c"] (re-matches street "Rosvokatu 1 as 001c")))
    (is (= ["Rosvokatu 1 as. 001c" "Rosvokatu " "1" nil nil nil "as." "00" "1" "c"] (re-matches street "Rosvokatu 1 as. 001c")))
    (is (= ["Rosvokatu 1 bst 001c" "Rosvokatu " "1" nil nil nil "bst" "00" "1" "c"] (re-matches street "Rosvokatu 1 bst 001c")))
    (is (= ["Rosvokatu 1 bst. 001c" "Rosvokatu " "1" nil nil nil "bst." "00" "1" "c"] (re-matches street "Rosvokatu 1 bst. 001c"))))
  (testing "address with multiple buildings on same plot"
    (is (= ["Ulvilantie 29/4 K 825" "Ulvilantie " "29" nil "/4" nil "K" "" "825" ""] (re-matches street "Ulvilantie 29/4 K 825")))))