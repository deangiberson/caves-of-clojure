(ns caves_of_clojure.ui.input
  (:use [caves_of_clojure.world :only [random-world smooth-world]]
        [caves_of_clojure.ui.core :only [->UI]]
        [caves_of_clojure.entities.player :only [move-player make-player]])
  (:require [lanterna.screen :as s]))

(defn move [[x y] [dx dy]]
  [(+ x dx) (+ y dy)])

(defn reset-game [game]
  (let [fresh-world (random-world)]
    (-> game
        (assoc :world (random-world))
        (assoc-in [:world :player] (make-player fresh-world))
        (assoc :uis [(->UI :play)]))))

(defmulti process-input
  (fn [game input]
    (:kind (last (:uis game)))))

(defmethod process-input :start [game input]
  (reset-game game))

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

    ;\s         (update-in game [:world] smooth-world)

    \h         (update-in game [:world] move-player :w)
    \j         (update-in game [:world] move-player :s)
    \k         (update-in game [:world] move-player :n)
    \l         (update-in game [:world] move-player :e)
    \y         (update-in game [:world] move-player :nw)
    \u         (update-in game [:world] move-player :ne)
    \b         (update-in game [:world] move-player :sw)
    \n         (update-in game [:world] move-player :se)

    game))

(defn get-input [game screen]
  (assoc game :input (s/get-key-blocking screen)))
