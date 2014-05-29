JHS-106
=======

Clojure implementation of Finnish address parsing based on [JHS-106 specification] (http://www.jhs-suositukset.fi/suomi/jhs106).
Sadly the specification is available only in finnish.


## Latest version

```clojure
[tomimas/jhs-106 "0.2.0"]
```

## Examples

Simple output:
```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/simple-parse "piippolankatu 3")
{:street {:number "3", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3")
{:street {:number "3", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3 A")
{:street {:stairway "A", :number "3", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3 A 5")
{:street {:apartment "5", :stairway "A", :number "3", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 A 5")
{:street {:apartment "5", :stairway "A", :number "3-5", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 A 5b")
{:street {:apartment "5b", :stairway "A", :number "3-5", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3-5 as 2")
{:street {:apartment "2", :number "3-5", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3/5 as 1")
{:street {:apartment "1", :number "3/5", :name "piippolankatu"}}
user=> (j/simple-parse "piippolank. 3b as 1")
{:street {:apartment "1", :number "3b", :name "piippolankatu"}}
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
{:street {:apartmentnumber "34", :apartment "34", :numberpart "12", :number "12", :name "Ullas torg"}}
user=> (j/parse "Ullas tg 12 1a")
{:street {:apartmentpartition "a", :apartmentnumber "1", :apartment "1a", :numberpart "12", :number "12",
          :name "Ullas torg"}}
user=> (j/parse "Tarkk'ampujank. 12-14 B 34b")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :stairway "B", :endnumber "14",
          :startnumber "12", :number "12-14", :name "Tarkk'ampujankatu"}}
user=> (j/parse "Mikki Hiiren p. 12/4b as. 034b")
{:street {:apartmentpartition "b", :apartmentnumber "34", :apartment "34b", :numberpartition "b",
          :building "4", :numberpart "12", :number "12/4b", :name "Mikki Hiiren polku"}}
```

## Eclipse

By running 'lein eclipse', the Eclipse project is created for IDE development.

## License

Copyright © 2014 Tomi Suuronen

Distributed under the Eclipse Public License, the same as Clojure.
