(ns caves_of_clojure.ui.input
  (:use [caves_of_clojure.world :only [random-world smooth-world]]
        [caves_of_clojure.ui.core :only [->UI]])
  (:require [lanterna.screen :as s]))

(defn move [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defmulti process-input
  (fn [game input]
    (:kind (last (:uis game)))))

(defmethod process-input :start [game input]
  (-> game
      (assoc :world (random-world))
      (assoc :uis [(->UI :play)])))

(defmethod process-input :win [game input]
  (if (= input :escape)
    (assoc game :uis [])
    (assoc game :uis [(->UI :start)])))

(defmethod process-input :lose [game input]
  (if (= input :escape)
    (assoc game :uis [])
    (assoc game :uis [(->UI :start)])))

(defmethod process-input :play [game input]
  (case input
    :enter     (assoc game :uis [(->UI :win)])
    :backspace (assoc game :uis [(->UI :lose)])
    \q         (assoc game :uis [])

    \s         (update-in game [:world] smooth-world)

    \h         (update-in game [:location] move [-1 0])
    \j         (update-in game [:location] move [0 1])
    \k         (update-in game [:location] move [0 -1])
    \l         (update-in game [:location] move [1 0])

    \H         (update-in game [:location] move [-5 0])
    \J         (update-in game [:location] move [0 5])
    \K         (update-in game [:location] move [0 -5])
    \L         (update-in game [:location] move [5 0])

    game))

(defn get-input [game screen]
  (assoc game :input (s/get-key-blocking screen)))
