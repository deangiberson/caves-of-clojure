(ns caves_of_clojure.entities.player
  (:use [caves_of_clojure.entities.core :only [Entity add-aspect]]
        [caves_of_clojure.entities.aspects.mobile :only [Mobile move can-move?]]
        [caves_of_clojure.entities.aspects.digger :only [Digger dig can-dig?]]
        [caves_of_clojure.entities.aspects.attacker :only [Attacker attack]]
        [caves_of_clojure.coords :only [destination-coords]]
        [caves_of_clojure.world :only [get-entity-at]]))

(defrecord Player [id glyph color location])

(extend-type Player Entity
             (tick [this world]
               world))

(add-aspect Player Mobile)
(add-aspect Player Digger)
(add-aspect Player Attacker)

(defn make-player [location]
  (->Player :player "@" :white location))

(defn move-player [world dir]
  (let [player (get-in world [:entities :player])
        target (destination-coords (:location player) dir)
        entity-at-target (get-entity-at world target)]
    (cond
     entity-at-target (attack player world entity-at-target)
     (can-move? player world target) (move player world target)
     (can-dig? player world target) (dig player world target)
     :else world)))
