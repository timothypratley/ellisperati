(ns ^:figwheel-hooks ellisperati.main
  (:require [ellisperati.view :as v]
            [ellisperati.macros :as m]
            [goog.dom :as gdom]
            [reagent.core :as r]
            ["react-dom/client" :as rc]))

(defonce root
  (some-> (gdom/getElement "app")
    (rc/createRoot)))

(defn mount-app-element []
  (when root
    (.render root (r/as-element [v/app]))))

(defonce app
  (do (mount-app-element)
      :done))

(defn ^:after-load on-reload []
  (mount-app-element))
