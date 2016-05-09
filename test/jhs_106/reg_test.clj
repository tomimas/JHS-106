(ns jhs-106.reg-test
  (:require [clojure.test :refer :all]
            [jhs-106.reg :refer :all]))

(deftest should-match-street-name
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
    (is (= "\u00C5lans V. v\u00E5n." (re-matches streetName "\u00C5lans V. v\u00E5n.")))
    (is (= "W\u00FCrthinkatu" (re-matches streetName "W\u00FCrthinkatu")))
    (is (= "W\u00DCRTHINKATU" (re-matches streetName "W\u00DCRTHINKATU")))
    (is (= "T\u0161ehovs gata" (re-matches streetName "T\u0161ehovs gata")))
    (is (= "\u0160kodan penger" (re-matches streetName "\u0160kodan penger")))
    (is (= "Fid\u017Ein ranta" (re-matches streetName "Fid\u017Ein ranta")))
    (is (= "Tohtori \u017Divagon aukio" (re-matches streetName "Tohtori \u017Divagon aukio")))))

(deftest should-match-abbreviated-street-name
  (testing "Abbreviated street name matching"
           (doseq [v (vals abbreviations)]
             (is (= (str "Rosvo" v) (re-matches streetName (str "Rosvo" v)))))))

(deftest should-match-abbreviated-multipart-street-name
  (testing "Abbreviated street name matching"
           (doseq [v (vals abbreviations)]
             (is (= (str "Iso Rosvon " v) (re-matches streetName (str "Iso Rosvon " v)))))))

(deftest should-match-street-number
  (testing "Street number regexp matching"
    (is (= ["1" "1" nil nil nil nil] (re-matches streetNumber "1")))
    (is (= ["1z" "1" "z" nil nil nil] (re-matches streetNumber "1z")))
    (is (= ["1Z" "1" "Z" nil nil nil] (re-matches streetNumber "1Z")))
    (is (= nil (re-matches streetNumber "a")))
    (is (= nil (re-matches streetNumber "B")))
    (is (= ["1-3" "1" nil "3" nil nil] (re-matches streetNumber "1-3")))))

(deftest should-match-full-street
  (testing "full street"
    (is (= ["Rosvokatu 1" "Rosvokatu " "1" nil nil nil nil nil nil nil] (re-matches street "Rosvokatu 1")))
    (is (= ["Rosvokatu 1c" "Rosvokatu " "1" "c" nil nil nil nil nil nil] (re-matches street "Rosvokatu 1c")))
    (is (= ["Rosvokatu 21-23" "Rosvokatu " "21" nil "23" nil nil nil  nil nil] (re-matches street "Rosvokatu 21-23")))
    (is (= ["Urhean Rosvon katu 1" "Urhean Rosvon katu " "1" nil nil nil nil nil nil nil] (re-matches street "Urhean Rosvon katu 1")))
    (is (= ["Urhean Rosvon katu 1d" "Urhean Rosvon katu " "1" "d" nil nil nil nil nil nil] (re-matches street "Urhean Rosvon katu 1d")))
    (is (= ["Urhean Rosvon katu 4-6" "Urhean Rosvon katu " "4" nil "6" nil nil nil nil nil] (re-matches street "Urhean Rosvon katu 4-6"))))
  (testing "full street with stairway"
    (is (= ["Rosvokatu 1 B" "Rosvokatu " "1" nil nil nil nil "B" nil nil] (re-matches street "Rosvokatu 1 B"))))
  (testing "full street with apartment"
    (is (= ["Rosvokatu 1 B 1" "Rosvokatu " "1" nil nil nil nil "B" "1" nil] (re-matches street "Rosvokatu 1 B 1")))
    (is (= ["Rosvokatu 1 as 1" "Rosvokatu " "1" nil nil nil nil "as" "1" nil] (re-matches street "Rosvokatu 1 as 1")))
    (is (= ["Rosvokatu 1 as. 1" "Rosvokatu " "1" nil nil nil nil "as." "1" nil] (re-matches street "Rosvokatu 1 as. 1")))
    (is (= ["Rosvokatu 1 bst 1" "Rosvokatu " "1" nil nil nil nil "bst" "1" nil] (re-matches street "Rosvokatu 1 bst 1")))
    (is (= ["Rosvokatu 1 bst. 1" "Rosvokatu " "1" nil nil nil nil "bst." "1" nil] (re-matches street "Rosvokatu 1 bst. 1")))
    (is (= ["Rosvokatu 1 B 1c" "Rosvokatu " "1" nil nil nil nil "B" "1" "c"] (re-matches street "Rosvokatu 1 B 1c")))
    (is (= ["Rosvokatu 1 b 1C" "Rosvokatu " "1" nil nil nil nil "b" "1" "C"] (re-matches street "Rosvokatu 1 b 1C")))
    (is (= ["Rosvokatu 1 as 1c" "Rosvokatu " "1" nil nil nil nil "as" "1" "c"] (re-matches street "Rosvokatu 1 as 1c")))
    (is (= ["Rosvokatu 1 as. 1c" "Rosvokatu " "1" nil nil nil nil "as." "1" "c"] (re-matches street "Rosvokatu 1 as. 1c")))
    (is (= ["Rosvokatu 1 as. 1C" "Rosvokatu " "1" nil nil nil nil "as." "1" "C"] (re-matches street "Rosvokatu 1 as. 1C")))
    (is (= ["Rosvokatu 1 bst 1c" "Rosvokatu " "1" nil nil nil nil "bst" "1" "c"] (re-matches street "Rosvokatu 1 bst 1c")))
    (is (= ["Rosvokatu 1 bst. 1c" "Rosvokatu " "1" nil nil nil nil "bst." "1" "c"] (re-matches street "Rosvokatu 1 bst. 1c"))))
  (testing "full street with leading zeroes in apartment number"
    (is (= ["Rosvokatu 1 B 001" "Rosvokatu " "1" nil nil nil nil "B" "1" nil] (re-matches street "Rosvokatu 1 B 001")))
    (is (= ["Rosvokatu 1 b 001" "Rosvokatu " "1" nil nil nil nil "b" "1" nil] (re-matches street "Rosvokatu 1 b 001")))
    (is (= ["Rosvokatu 1 as 001" "Rosvokatu " "1" nil nil nil nil "as" "1" nil] (re-matches street "Rosvokatu 1 as 001")))
    (is (= ["Rosvokatu 1 as. 001" "Rosvokatu " "1" nil nil nil nil "as." "1" nil] (re-matches street "Rosvokatu 1 as. 001")))
    (is (= ["Rosvokatu 1 bst 001" "Rosvokatu " "1" nil nil nil nil "bst" "1" nil] (re-matches street "Rosvokatu 1 bst 001")))
    (is (= ["Rosvokatu 1 bst. 001" "Rosvokatu " "1" nil nil nil nil "bst." "1" nil] (re-matches street "Rosvokatu 1 bst. 001")))
    (is (= ["Rosvokatu 1 B 001c" "Rosvokatu " "1" nil nil nil nil "B" "1" "c"] (re-matches street "Rosvokatu 1 B 001c")))
    (is (= ["Rosvokatu 1 b 001c" "Rosvokatu " "1" nil nil nil nil "b" "1" "c"] (re-matches street "Rosvokatu 1 b 001c")))
    (is (= ["Rosvokatu 1 as 001c" "Rosvokatu " "1" nil nil nil nil "as" "1" "c"] (re-matches street "Rosvokatu 1 as 001c")))
    (is (= ["Rosvokatu 1 as. 001c" "Rosvokatu " "1" nil nil nil nil "as." "1" "c"] (re-matches street "Rosvokatu 1 as. 001c")))
    (is (= ["Rosvokatu 1 bst 001c" "Rosvokatu " "1" nil nil nil nil "bst" "1" "c"] (re-matches street "Rosvokatu 1 bst 001c")))
    (is (= ["Rosvokatu 1 bst. 001c" "Rosvokatu " "1" nil nil nil nil "bst." "1" "c"] (re-matches street "Rosvokatu 1 bst. 001c"))))
  (testing "address with multiple buildings on same plot"
    (is (= ["Ulvilantie 29/4 K 825" "Ulvilantie " "29" nil nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29/4 K 825")))
    (is (= ["Ulvilantie 29/4 k 825" "Ulvilantie " "29" nil nil nil "4" "k" "825" nil] (re-matches street "Ulvilantie 29/4 k 825")))
    (is (= ["Ulvilantie 29 rak. 4 K 825" "Ulvilantie " "29" nil nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29 rak. 4 K 825")))
    (is (= ["Ulvilantie 29 rak. 4 k 825" "Ulvilantie " "29" nil nil nil "4" "k" "825" nil] (re-matches street "Ulvilantie 29 rak. 4 k 825")))
    (is (= ["Ulvilantie 29 rak 4 K 825" "Ulvilantie " "29" nil nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29 rak 4 K 825")))
    (is (= ["Ulvilantie 29 rak 4 k 825" "Ulvilantie " "29" nil nil nil "4" "k" "825" nil] (re-matches street "Ulvilantie 29 rak 4 k 825")))
    (is (= ["Ulvilantie 29a/4 K 825" "Ulvilantie " "29" "a" nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29a/4 K 825")))
    (is (= ["Ulvilantie 29a/4 K 825c" "Ulvilantie " "29" "a" nil nil "4" "K" "825" "c"] (re-matches street "Ulvilantie 29a/4 K 825c")))
    (is (= ["Ulvilantie 29A/4 k 825" "Ulvilantie " "29" "A" nil nil "4" "k" "825" nil] (re-matches street "Ulvilantie 29A/4 k 825")))
    (is (= ["Ulvilantie 29A/4 k 825C" "Ulvilantie " "29" "A" nil nil "4" "k" "825" "C"] (re-matches street "Ulvilantie 29A/4 k 825C")))
    (is (= ["Ulvilantie 29a rak. 4 K 825" "Ulvilantie " "29" "a" nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29a rak. 4 K 825")))
    (is (= ["Ulvilantie 29a rak 4 K 825" "Ulvilantie " "29" "a" nil nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29a rak 4 K 825")))
    (is (= ["Ulvilantie 29A rak 4 k 825c" "Ulvilantie " "29" "A" nil nil "4" "k" "825" "c"] (re-matches street "Ulvilantie 29A rak 4 k 825c")))
    (is (= ["Ulvilantie 29-31/4 K 825" "Ulvilantie " "29" nil "31" nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29-31/4 K 825")))
    (is (= ["Ulvilantie 29-31 rak. 4 K 825" "Ulvilantie " "29" nil "31" nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29-31 rak. 4 K 825")))
    (is (= ["Ulvilantie 29-31 rak 4 K 825" "Ulvilantie " "29" nil "31" nil "4" "K" "825" nil] (re-matches street "Ulvilantie 29-31 rak 4 K 825")))
    (is (= ["Ulvilantie 29-31b rak 4 K 825" "Ulvilantie " "29" nil "31" "b" "4" "K" "825" nil] (re-matches street "Ulvilantie 29-31b rak 4 K 825")))))

(deftest should-match-postal
  (testing "Postcode with FI country code"
    (is (= ["FI-00740" "FI-00740" nil] (re-matches postal "FI-00740"))))
  (testing "Postcode regexp matching"
    (is (= ["00740" "00740" nil] (re-matches postal "00740"))))
  (testing "Postoffice regexp matching"
    (is (= ["VIERTOLA" nil "VIERTOLA"] (re-matches postal "VIERTOLA")))
    (is (= ["Viertola" nil "Viertola"] (re-matches postal "Viertola")))
    (is (= ["vIeRToLA" nil "vIeRToLA"] (re-matches postal "vIeRToLA")))
    (is (= ["Vihti KK" nil "Vihti KK"] (re-matches postal "Vihti KK")))
    (is (= ["HYVINK\u00C4\u00C4" nil "HYVINK\u00C4\u00C4"] (re-matches postal "HYVINK\u00C4\u00C4")))
    (is (= ["\u00D6r\u00F6" nil "\u00D6r\u00F6"] (re-matches postal "\u00D6r\u00F6"))))
  (testing "Postcode and postoffice regexp matching"
    (is (= ["00940 VIERTOLA" "00940" "VIERTOLA"] (re-matches postal "00940 VIERTOLA")))
    (is (= ["00940 vIeRToLA" "00940" "vIeRToLA"] (re-matches postal "00940 vIeRToLA")))
    (is (= ["00940 vIeRToLA kK" "00940" "vIeRToLA kK"] (re-matches postal "00940 vIeRToLA kK")))
    (is (= ["91940 HYVINK\u00C4\u00C4" "91940" "HYVINK\u00C4\u00C4"] (re-matches postal "91940 HYVINK\u00C4\u00C4")))
    (is (= ["06660 \u00C5land" "06660" "\u00C5land"] (re-matches postal "06660 \u00C5land")))))
