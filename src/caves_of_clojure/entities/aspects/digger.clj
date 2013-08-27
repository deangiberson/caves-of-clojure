(ns caves_of_clojure.entities.aspects.digger
  (:use [caves_of_clojure.entities.core :only [defaspect]]
        [caves_of_clojure.world :only [check-tile set-tile-floor]]))

(defaspect Digger
  (dig [this world dest]
    {:pre [(can-dig? this world dest)]}
    (set-tile-floor world dest))
  (can-dig? [this world dest]
    (check-tile world dest #{:wall})))
