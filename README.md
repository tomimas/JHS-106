JHS-106
=======

Clojure implementation of Finland's postal address parsing, based on [JHS-106 specification] (https://www.suomidigi.fi/ohjeet-ja-tuki/jhs-suositukset/jhs-106-postiosoite-vanhentunut).
Sadly the specification is available only in Finnish and has been deprecated 2020.

## Latest version

[![Build Status](https://travis-ci.org/tomimas/JHS-106.svg?branch=master)](https://travis-ci.org/tomimas/JHS-106)

[![Clojars Project](http://clojars.org/tomimas/jhs-106/latest-version.svg)](http://clojars.org/tomimas/jhs-106)

## Examples

Simple output:
```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/simple-parse "piippolankatu 3")
{:street {:number "3", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3")
{:street {:number "3", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3 A")
{:street {:stairway "A", :number "3", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3 A 5")
{:street {:apartment "5", :stairway "A", :number "3", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 A 5")
{:street {:apartment "5", :stairway "A", :number "3-5", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 A 5b")
{:street {:apartment "5b", :stairway "A", :number "3-5", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 as 2")
{:street {:apartment "2", :number "3-5", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3/5 as 1")
{:street {:apartment "1", :number "3/5", :name "Piippolankatu"}}
user=> (j/simple-parse "piippolank. 3b as 1")
{:street {:apartment "1", :number "3b", :name "Piippolankatu"}}
user=> (j/simple-parse "Aatos Leikolan puisto 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan puisto"}}
user=> (j/simple-parse "Aatos Leikolan p. 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan polku"}}
user=> (j/simple-parse "Aatos Leikolan ps. 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan puisto"}}
user=> (j/simple-parse "Ullas g. 12 1")
{:street {:apartment "1", :number "12", :name "Ullas gata"}}
```

Precise output:
```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/parse "Ullas tg 12 bst. 34")
{:street {:apartmentnumber "34", :apartment "34", :numberpart "12", :number "12",
          :name "Ullas torg"}}
user=> (j/parse "Ullas tg 12 1a")
{:street {:apartmentpartition "a", :apartmentnumber "1", :apartment "1a", :numberpart "12",
          :number "12", :name "Ullas torg"}}
user=> (j/parse "Tarkk'ampujank. 12-14 B 34b")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :endnumber "14", :startnumber "12", :number "12-14", :name "Tarkk'ampujankatu"}}
user=> (j/parse "Mikki Hiiren p. 12a/4 as. 034b")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :building "4",
          :numberpartition "a", :numberpart "12", :number "12a/4",
          :name "Mikki Hiiren polku"}}
user=> (j/parse "Tarkk'ampujank. 12-14 rak. 2 B 34b")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :building "2", :endnumber "14", :startnumber "12", :number "12-14/2",
          :name "Tarkk'ampujankatu"}}
```

Formats output:
```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/parse "Tarkk'ampujank. 12A rak. 2 b 034B")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :building "2", :numberpart "12", :number "12a/2", :name "Tarkk'ampujankatu"}}
```

Postcode/postoffice support:
```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/parse "Tarkk'ampujank. 12A rak. 2 b 034B\n05830 HYVINKÄÄ")
{:postoffice "HYVINKÄÄ",
 :postcode "05830",
 :street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :building "2", :numberpartition "a", :numberpart "12", :number "12a/2",
          :name "Tarkk'ampujankatu"}}
user=> (j/parse "Tarkk'ampujank. 12A rak. 2 b 034B
  #_=> 05830 HYVINKÄÄ")
{:postoffice "HYVINKÄÄ",
 :postcode "05830",
 :street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :building "2", :numberpartition "a", :numberpart "12", :number "12a/2",
          :name "Tarkk'ampujankatu"}}
user=> (j/parse "Tarkk'ampujank. 12A rak. 2 b 034B, 05830 Hyvinkää")
{:postoffice "HYVINKÄÄ",
 :postcode "05830",
 :street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B",
          :building "2", :numberpartition "a", :numberpart "12", :number "12a/2",
          :name "Tarkk'ampujankatu"}}
```
## Eclipse

By running 'lein eclipse', the Eclipse project files are created for IDE development.

## License

Copyright &copy; 2014-2016 Tomi Suuronen

Distributed under the Eclipse Public License, the same as Clojure.
