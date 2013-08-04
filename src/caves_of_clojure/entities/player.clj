(ns caves_of_clojure.entities.player
  (:use [caves_of_clojure.entities.core :only [Entity]]
        [caves_of_clojure.entities.aspects.mobile :only [Mobile move can-move?]]
        [caves_of_clojure.entities.aspects.digger :only [Digger dig can-dig?]]
        [caves_of_clojure.coords :only [destination-coords]]
        [caves_of_clojure.world :only [find-empty-tile get-tile-kind set-tile-floor]]))

(defrecord Player [id glyph color location])

(defn check-tile
  "Check that the tile at the destination passes the given
  predicate."
  [world dest pred]
  (pred (get-tile-kind world dest)))

(extend-type Player Entity
             (tick [this world]
               world))

(extend-type Player Mobile
             (move [this world dest]
               {:pre [(can-move? this world dest)]}
               (assoc-in world [:entities :player :location] dest))
             (can-move? [this world dest]
               (check-tile world dest #{:floor})))

(extend-type Player Digger
             (dig [this world dest]
               {:pre [(can-dig? this world dest)]}
               (set-tile-floor world dest))
             (can-dig? [this world dest]
               (check-tile world dest #{:wall})))

(defn make-player [location]
  (->Player :player "@" :white location))

(defn move-player [world dir]
  (let [player (get-in world [:entities :player])
        target (destination-coords (:location player) dir)]
    (cond
     (can-move? player world target) (move player world target)
     (can-dig? player world target) (dig player world target)
     :else world)))
