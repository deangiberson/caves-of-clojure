(ns caves_of_clojure.coords)

(defn offset-coords
  "Offset the starting coordinate by the given amount, returning the
  resulting coordinate."  
  [[x y] [dx dy]] 
  [(+ x dx) (+ y dy)])

(defn dir-to-offset
  "Convert a direction to the offset for moving in that direction."
  [dir]
  (case dir
    :w [-1 0]
    :e [1 0]
    :n [0 -1]
    :s [0 1]
    :nw [-1 -1]
    :ne [1 -1]
    :sw [-1 1]
    :se [1 1]))

(defn destination-coords
  "Take an origin's coords and a directions and return the destination."
  [origin dir]
  (offset-coords origin (dir-to-offset dir)))
