(ns jhs-106.reg)

(def streetName #"([a-zA-Z]+[\s]{0,1})+")
(def streetNumber #"[\d]+[a-z]{0,1}")
(def street #"((?:[a-zA-Z]+[\s]{0,1})+)([\d]+[a-z]{0,1})?")
