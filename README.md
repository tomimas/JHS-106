JHS-106
=======

Clojure implementation of Finnish address parsing based on [JHS-106 specification] (http://www.jhs-suositukset.fi/suomi/jhs106).
Sadly the specification is available only in finnish.


## Latest version

```clojure
[tomimas/jhs-106 "0.2.0"]
```

## Examples

```clojure
user=> (require '[jhs-106.core :as j])
nil
user=> (j/parse "piippolankatu 3")
{:street {:number "3", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3")
{:street {:number "3", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3 A")
{:street {:stairway "A", :number "3", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3 A 5")
{:street {:apartment "5", :stairway "A", :number "3", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3-5 A 5")
{:street {:apartment "5", :stairway "A", :number "3-5", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3-5 A 5b")
{:street {:apartment "5b", :stairway "A", :number "3-5", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3-5 as 2")
{:street {:apartment "2", :number "3-5", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3/5 as 1")
{:street {:apartment "1", :number "3/5", :name "piippolankatu"}}
user=> (j/parse "piippolank. 3b as 1")
{:street {:apartment "1", :number "3b", :name "piippolankatu"}}
user=> (j/parse "Aatos Leikolan puisto 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan puisto"}}
user=> (j/parse "Aatos Leikolan p. 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan polku"}}
user=> (j/parse "Aatos Leikolan ps. 3 B 1")
{:street {:apartment "1", :stairway "B", :number "3", :name "Aatos Leikolan puisto"}}
```

## Eclipse

By running 'lein eclipse', the Eclipse project is created for IDE development.

## License

Copyright © 2014 Tomi Suuronen

Distributed under the Eclipse Public License, the same as Clojure.
