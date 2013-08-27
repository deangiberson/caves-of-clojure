(ns caves_of_clojure.entities.core)

(def ids (ref 0))

(defprotocol Entity
  (tick [this world]
    "Update the world to handle the passing of a tick for this entity."))

(defn get-id []
  (dosync
   (let [id @ids]
     (alter ids inc)
     id)))

(defn make-fnmap
  "Make a function map out of the given sequence of fnspecs.

  A function map is a map of functions that you'd pass to extend.  For example,
  this sequence of fnspecs:

  ((foo [a] (println a)
   (bar [a b] (+ a b)))

  Would be turned into this fnmap:

  {:foo (fn [a] (println a))
   :bar (fn [a b] (+ a b))}

  "
  [fns]
  (into {} (for [[label fntail] (map (juxt first rest) fns)]
             [(keyword label)
              `(fn ~@fntail)])))

(defn make-fnheads
  "Make a sequence of fnheads of of the given sequence of fnspecs.

  A fnhead is a sequence of (name args) like you'd pass to defprotocol.  For
  example, this sequence of fnspecs:

  ((foo [a] (println a))
   (bar [a b] (+ a b)))

  Would be turned into this sequence of fnheads:

  ((foo [a])
   (bar [a b]))

  "
  [fns]
  (map #(take 2 %) fns))

(defmacro add-aspect [entity aspect & fns]
  (let [fnmap (make-fnmap fns)]
    `(extend ~entity ~aspect (merge (:defaults (meta ~aspect))
                                    ~fnmap))))

(defmacro defaspect [label & fns]
  (let [fnmap (make-fnmap fns)
        fnheads (make-fnheads fns)]
    `(do
       (defprotocol ~label
         ~@fnheads)
       (def ~label
         (with-meta ~label {:defaults ~fnmap})))))
