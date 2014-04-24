(ns jhs-106.core-test
  (:require [clojure.test :refer :all]
            [jhs-106.core :refer :all]))

(deftest shouldParseStreetBasedOnInput
  (testing "Street name parsing"
    (is (= {:street {:name "Kettulankatu"}} (parse "Kettulankatu")))
    (is (= {:street {:name "Urho Kekkosen katu"}} (parse "Urho Kekkosen katu"))))
  (testing "Street number parsing"
    (is (= {:street {:name "Kettulankatu"
                    :number "2"}} (parse "Kettulankatu 2")))
    (is (={ :street {:name "Kettulankatu"
                    :number "2a"}} (parse "Kettulankatu 2a")))))
