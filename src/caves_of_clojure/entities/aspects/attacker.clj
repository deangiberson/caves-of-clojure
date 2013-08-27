(ns caves_of_clojure.entities.aspects.attacker
  (:use [caves_of_clojure.entities.aspects.destructible :only [Destructible take-damage]]
        [caves_of_clojure.entities.core :only [defaspect]]))

(defaspect Attacker
  (attack [this world target]
    {:pre [(satisfies? Destructible target)]}
    (let [damage 1]
      (take-damage target world damage))))
