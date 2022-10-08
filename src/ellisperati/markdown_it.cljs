(ns ellisperati.markdown-it
  (:require [dompurify]
            [markdown-it]
            [markdown-it-mathjax3]
            [markdown-it-emoji]))

(defonce ^js mdi
  (doto (new markdown-it #js{:linkify     true
                             :typographer true
                             :breaks      true})
    (.use markdown-it-mathjax3)
    (.use markdown-it-emoji)))

(defn md [props markdown-str]
  (let [s (if (string? markdown-str)
            markdown-str
            "")
        html-str (->> (.render mdi s)
                      (dompurify/sanitize))]
    [:div (merge-with merge
                      {:className "md"
                       :dangerouslySetInnerHTML {:__html html-str}}
                      props)]))
