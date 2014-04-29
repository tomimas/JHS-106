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

(def abbreviations (array-map (keyword "L\u00E4ntinen") "L\u00E4nt."
                              :Pohjoinen "Pohj."
                              :aukio "auk."
                              :bostad "bst."
                              :kaari "kri"
                              :puistikko "pko."
                              :strand "str."
                              :penger "pgr."
                              :kerros "krs"
                              (keyword "skv\u00E4r") "skv."
                              ;(keyword "skv\u00E4ret") "skv."
                              (keyword "v\u00E5ning") "v\u00E5n."
                              (keyword "gr\u00E4nd") "gr."
                              ;(keyword "gr\u00E4nden") "gr."
                              :alue "al."
                              :asunto "as."
                              :bygata "bg."
                              ;:bygatan "bg."
                              :brinken "br."
                              (keyword "Etel\u00E4inen") "Et."
                              :torg "tg"
                              ;:torget "tg"
                              :taival "tvl"
                              (keyword "v\u00E4yl\u00E4") "vl\u00E4"
                              :kuja "kj."
                              (keyword "kyl\u00E4") "kl."
                              :puisto "ps."
                              :stig "st."
                              ;:stigen "st."
                              :park "pk."
                              ;:parken "pk."
                              (keyword "str\u00E5k") "sk."
                              ;(keyword "str\u00E5ket") "sk."
                              (keyword "It\u00E4inen") "It."
                              :ranta "rt."
                              :rinne "rn."
                              :tori "tr."
                              :raitti "r."
                              :tie "t."
                              :led "l."
                              ;:leden "l."
                              :katu "k."
                              :polku "p."
                              :gata "g."
                              ;:gatan "g."
                              :Norra "N."
                              (keyword "S\u00F6dra") "S."
                              (keyword "v\u00E4g") "v."
                              ;(keyword "v\u00E4gen") "v."
                              (keyword "V\u00E4stra") "V."
                              (keyword "\u00D6stra") "\u00D6."))

(def streetName (re-pattern STREET_NAME))
(def streetNumber (re-pattern STREET_NUMBER))
(def street (re-pattern (str "(" STREET_NAME ")(" STREET_NUMBER ")?")))
