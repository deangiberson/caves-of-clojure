(ns caves_of_clojure.entities.aspects.digger)

(defprotocol Digger
  (dig [this world target]
    "Dig at location.")
  (can-dig? [this world target]
    "Can the entity dig at this location."))