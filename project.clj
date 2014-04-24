(defproject tomimas/jhs-106 "0.1.1"
  :description "Clojure implementation of Finnish address parsing based on JHS-106 specification."
  :url "https://github.com/tomimas/JHS-106"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :signing {:gpg-key "tomi@suuronen.eu"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :deploy-repositories [["clojars" {:creds :gpg}]]
  :scm {:name "git"
        :url "https://github.com/tomimas/JHS-106"}
  :profiles {:dev {:plugins [[lein2-eclipse "2.0.0"]]}})
