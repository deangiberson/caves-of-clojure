(ns caves_of_clojure.entities.silverfish
  (:use [caves_of_clojure.entities.core :only [Entity get-id add-aspect]]
        [caves_of_clojure.entities.aspects.destructible :only [Destructible]]
        [caves_of_clojure.entities.aspects.mobile :only [Mobile move can-move?]]
        [caves_of_clojure.world :only [get-entity-at]]
        [caves_of_clojure.coords :only [neighbors]]))

(defrecord Silverfish [id glyph color location hp])

(defn make-silverfish [location]
  (->Silverfish (get-id) "~" :white location 1))

(extend-type Silverfish Entity
  (tick [this world]
    (let [target (rand-nth (neighbors (:location this)))]
      (if (get-entity-at world target)
        world
        (move this world target)))))

(add-aspect Silverfish Mobile
  (can-move? [this world dest]
    (not (get-entity-at world dest))))

(add-aspect Silverfish Destructible)
