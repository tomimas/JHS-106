(ns jhs-106.reg)

(def CAPITAL_A_WITH_DIARESIS "\u00C4") ; Ä
(def SMALL_A_WITH_DIARESIS "\u00E4") ; ä
(def CAPITAL_A_WITH_RING_ABOVE "\u00C5") ; Å
(def SMALL_A_WITH_RING_ABOVE "\u00E5") ; å
(def CAPITAL_O_WITH_DIARESIS "\u00D6") ; Ö
(def SMALL_O_WITH_DIARESIS "\u00F6") ; ö
(def CAPITAL_E_WITH_ACUTE "\u00C9") ; É
(def SMALL_E_WITH_ACUTE "\u00E9") ; é
(def CAPITAL_U_WITH_UMLAUT "\u00DC") ; Ü
(def SMALL_U_WITH_UMLAUT "\u00FC") ; ü
(def ACUTE_ACCENT "\u00B4") ; ´
(def APOSTROPHE "\u0027") ; '
(def SMALL_LETTERS "a-z")
(def CAPITAL_LETTERS "A-Z")

(def LATIN_LETTERS (str SMALL_O_WITH_DIARESIS
                        SMALL_A_WITH_DIARESIS
                        SMALL_A_WITH_RING_ABOVE
                        SMALL_E_WITH_ACUTE
                        SMALL_U_WITH_UMLAUT
                        CAPITAL_O_WITH_DIARESIS
                        CAPITAL_A_WITH_DIARESIS
                        CAPITAL_A_WITH_RING_ABOVE
                        CAPITAL_E_WITH_ACUTE
                        CAPITAL_U_WITH_UMLAUT))

(def OTHER (str APOSTROPHE
                "-/\\.:"
                ACUTE_ACCENT))

(def ALL_LETTERS (str SMALL_LETTERS
                      CAPITAL_LETTERS
                      LATIN_LETTERS))

(def ALL_CHARACTERS (str ALL_LETTERS
                         OTHER))

(def STREET_NAME (str "(?:[" ALL_CHARACTERS "]+[\\s]{0,1})+"))
(def STREET_NUMBER (str "(?:([0-9]+)(?:([" ALL_LETTERS "][-]?[" ALL_LETTERS "]?)?|(?:[-]([0-9]+)(?:([" ALL_LETTERS "])?)?)?)?(?:(?:[/]|(?:[\\s]rak[\\.]?[\\s]))([0-9]+))?)"))
(def APARTMENT_ABBREVIATIONS "as|as\\.|bst|bst\\.")
(def STREET_STAIRWAY (str "(?:[" ALL_LETTERS "]{1}|" APARTMENT_ABBREVIATIONS ")"))
(def APARTMENT (str "(?:[0]{0,2})([0-9]{1,3})([" ALL_LETTERS "])?"))
(def POSTCODE (str "[\\d]{5}"))
(def POSTOFFICE (str "(?:[" ALL_LETTERS "]+[\\s]{0,1})+"))

(def ALLOWED_CHARACTERS (str "[" ALL_CHARACTERS "01-9-/\\.:\\s]"))

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
(def street (re-pattern (str "(" STREET_NAME ")(?:" STREET_NUMBER ")?[\\s]{0,1}(" STREET_STAIRWAY ")?[\\s]{0,1}(?:" APARTMENT ")?")))
(def postal (re-pattern (str "(" POSTCODE ")?[\\s]{0,1}(" POSTOFFICE ")?")))
(def allowedCharacters (re-pattern (str "^[" ALLOWED_CHARACTERS "]*$")))
