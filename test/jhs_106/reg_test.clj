(ns jhs-106.reg_test
  (:require [clojure.test :refer :all]
            [jhs-106.reg :refer :all]))

(deftest shouldMatch
  (testing "Street name rew matching"
    (is (= ["Rosvokatu" "Rosvokatu"] (re-matches streetName "Rosvokatu")))
    (is (= ["Urhean Rosvon katu" "katu"] (re-matches streetName "Urhean Rosvon katu"))))
  (testing "Street number reg matching"
    (is (= "1" (re-matches streetNumber "1")))
    (is (= "1z" (re-matches streetNumber "1z")))
    (is (= nil (re-matches streetNumber "a"))))
  (testing "full street"
    (is (= ["Rosvokatu 1" "Rosvokatu " "1"] (re-matches street "Rosvokatu 1")))
    (is (= ["Urhean Rosvon katu 1" "Urhean Rosvon katu " "1"] (re-matches street "Urhean Rosvon katu 1")))))
