(ns caves_of_clojure.entities.aspects.mobile)

(defprotocol Mobile
  (move [this world dest]
    "Move this entity to a new location.")
  (can-move? [this world dest]
    "Return whether the entity can mvoe to the new location."))