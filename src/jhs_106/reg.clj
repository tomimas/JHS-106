(ns jhs-106.reg)

(def CAPITAL_A_WITH_DIARESIS "\u00C4") ; Ä
(def SMALL_A_WITH_DIARESIS "\u00E4") ; ä
(def CAPITAL_A_WITH_RING_ABOVE "\u00C5") ; Å
(def SMALL_A_WITH_RING_ABOVE "\u00E5") ; å
(def CAPITAL_O_WITH_DIARESIS "\u00D6") ; Ö
(def SMALL_O_WITH_DIARESIS "\u00F6") ; ö
(def CAPITAL_E_WITH_ACUTE "\u00C9") ; É
(def SMALL_E_WITH_ACUTE "\u00E9") ; é
(def ACUTE_ACCENT "\u00B4") ; ´
(def APOSTROPHE "\u0027") ; '
(def SMALL_LETTERS "a-z")
(def CAPITAL_LETTERS "A-Z")

(def LATIN_SUPPORT (str SMALL_O_WITH_DIARESIS
                        SMALL_A_WITH_DIARESIS
                        SMALL_A_WITH_RING_ABOVE
                        SMALL_E_WITH_ACUTE
                        CAPITAL_O_WITH_DIARESIS
                        CAPITAL_A_WITH_DIARESIS
                        CAPITAL_A_WITH_RING_ABOVE
                        CAPITAL_E_WITH_ACUTE
                        APOSTROPHE
                        "-/\\.:"
                        ACUTE_ACCENT))

(def ALL_LETTERS (str SMALL_LETTERS
                      CAPITAL_LETTERS
                      LATIN_SUPPORT))

(def STREET_NAME (str "(?:[" ALL_LETTERS "]+[\\s]{0,1})+"))
(def STREET_NUMBER (str "(?:[0-9]+(?:[-][0-9]+)?(?:[" SMALL_LETTERS "])?)"))

(def streetName (re-pattern STREET_NAME))
(def streetNumber (re-pattern STREET_NUMBER))
(def street (re-pattern (str "(" STREET_NAME ")(" STREET_NUMBER ")?")))
