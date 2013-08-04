(ns caves_of_clojure.entities.lichen
  (:use [caves_of_clojure.entities.core :only [Entity get-id]]))

(defrecord Lichen [id glyph color location])

(defn make-lichen [location]
  (->Lichen (get-id) "F" :green location))

(extend-type Lichen Entity
             (tick [this world]
               world))
