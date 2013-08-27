(ns caves_of_clojure.entities.aspects.mobile
  (:use [caves_of_clojure.entities.core :only [defaspect]]
        [caves_of_clojure.world :only [is-empty?]]))

(defaspect Mobile
  (move [this world dest]
    {:pre [(can-move? this world dest)]}
    (assoc-in world [:entities (:id this) :location] dest))
  (can-move? [this world dest]
    (is-empty? world dest)))
