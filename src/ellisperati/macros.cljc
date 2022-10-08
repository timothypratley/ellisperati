(ns ellisperati.macros
  #?(:cljs (:require-macros [ellisperati.macros])))

#?(:clj (do (defmacro foo [& body] (str "bar" body))
            (intern 'clojure.core (with-meta 'foo (meta #'foo)) #'foo)
            (intern 'cljs.core (with-meta 'foo (meta #'foo)) #'foo)))
