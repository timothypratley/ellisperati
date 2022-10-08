(ns ellisperati.view
  (:require [clojure.string :as str]
            [ellisperati.ui :as ui]
            [goog.events.Keys :as k]
            [clojure.math :as math]
            [reagent.core :as reagent]
            [ellisperati.animation :as anim]
            [ellisperati.markdown-it :as md]))

(def default-theme
  {:pale          "#fcffe0"
   :solid         "#d0f040"
   :label         "#f0f0c8"
   :label-outline "#e0e0d8"
   :outline       "#afa050"
   :arrow         "#706010"
   :text          "#706010"
   :selected      "blue"
   :subselected   "lightblue"})

(def svg-default-props
  {:viewBox         "-5 -5 10 10"
   :stroke          (:outline default-theme)
   :stroke-width    0.1
   :stroke-linecap  "round"
   :stroke-linejoin "round"
   :fill            "none"
   :text-anchor     "middle"
   :xmlns           "http://www.w3.org/2000/svg"
   :style           {:background-color (:pale default-theme)
                     :border           (str "solid 1px " (:outline default-theme))
                     :border-radius    50}})

(defn svg [props & body]
  `[:svg ~(merge svg-default-props props)
    ~@body])

(defn fo [props & body]
  `[:foreignObject ~(merge {:style {:overflow :visible
                                    :position "relative"}}
                           (dissoc props :scale))
    [:div {:xmlns "http://www.w3.org/1999/xhtml"
           :style {:position         "absolute"
                   :width            "max-content"
                   :background-color ~(str (:label default-theme) "66")
                   :padding          5
                   :border-radius    5
                   :transform        ~(str "translate(-50%,-50%) scale(" (or (:scale props) 0.04) ")")}}
     ~@body]])

(defn label [x y s]
  [fo {:x x
       :y y}
   [md/md {} s]])

(def r2 (math/sqrt 2))
(def r3 (math/sqrt 3))

(def b' 3)
(def a' 4)
(def c' (math/sqrt (- (math/pow a' 2)
                      (math/pow b' 2))))

(defn isotri [{:keys [cx cy r phi]}]
  [:polygon {:points (str/join " " [(/ r3 -2) (/ 1 2)
                                    (/ r3 2) (/ 1 2)
                                    0 -1])}])

(defn hex-points [r]
  (let [dy (/ (* r 1.5) r3)
        dx (/ r 2)]
    [(- r) 0
     (- dx) (- dy)
     dx (- dy)
     r 0
     dx dy
     (- dx) dy]))

(defn hexagon [props r]
  [:polygon (merge
              {:points (hex-points r)}
              props)])

(defn g2 [props x y & more]
  `[:g ~(merge {:transform (str "translate(" x "," y ")")}
               props)
    ~@more])

(defn honeycomb []
  (reagent/with-let [t (reagent/atom 0)
                     colors (reagent/atom
                              (vec (repeatedly 121 #(rand-nth (vec (vals (dissoc default-theme :selected)))))))]
    (when (< @t 5)
      (reagent/next-tick #(swap! t + 0.1)))
    (let [i (rand-int 121)
          j (if (< i 61)
              (+ i (rand-int 10))
              (- i (rand-int 10)))]
      (swap! colors (fn [cols]
                      (assoc cols i (cols j)
                                  j (cols i)))))
    `[:g ~{:transform (str "scale(" (max 1 (- 6 @t)) ")")}
      ~@(for [x (range -3 4 2)
              y (range -5 6 2)]
          [g2 {:fill (:label default-theme)}
           (* r3 x) y
           [hexagon {:fill (get @colors (+ (+ x 3) (* 11 (+ y 5))))} 1]
           [g2 {} r3 1
            [hexagon {:fill (get @colors (+ (+ x 4) (* 11 (+ y 5))))} 1]]])]))

(defn hexagons []
  [:<>
   [:polygon {:stroke (:selected default-theme)
              :points (str/join " " [(* 3 (/ r3 -2)) (* 3 (/ 1 2))
                                     (* 3 (/ r3 2)) (* 3 (/ 1 2))
                                     0 (* 3 -1)])}]
   [hexagon {:transform "rotate(30)"} 3]
   [hexagon {} r3]
   [g2 {:stroke-opacity 0.5}
    0 1
    [:path {:d (str/join " " ['M (- r3) -1 'L r3 -1])}]
    [:path {:d (str/join " " ['M (/ r3 -2) -2.5 'L (/ r3 2) 0.5])}]
    [:path {:d (str/join " " ['M (/ r3 2) -2.5 'L (/ r3 -2) 0.5])}]]])

(defn goal []
  [:<>
   [:polygon {:points (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]
   [:g {:transform "translate(0,-1) rotate(180)"}
    [isotri {:r 1 :phi (/ math/PI 2)}]]
   [:path {:d (str/join " " ['M r3 0 'L 0 -3])}]
   [:ellipse {:rx     (/ r3 r2)
              :ry     (* (/ 3 2) r2)
              :stroke (:selected default-theme)}]])

(defn squash-circle []
  (reagent/with-let [t (reagent/atom 0)]
    (when (< @t (* (/ 3 2) math/PI))
      (reagent/next-tick #(swap! t + 0.02)))
    [:<>
     [:ellipse {:rx (+ b' (math/cos @t))
                :ry a'}]]))

(defn circle []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx 3 :ry 3}]
     (let [x (* 3 (math/cos @t))
           y (* 3 (math/sin @t))]
       [:g
        [:path {:d (str/join " " ['M 0 0 'L x y])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [label 0 -1 "$${{x^2} \\over {r^2}} + {{y^2} \\over {r^2}} = 1$$"]]))

(defn ellipse []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx b' :ry a'}]
     (let [x (* b' (math/cos @t))
           y (* a' (math/sin @t))]
       [:g
        [:path {:d (str/join " " ['M 0 0 'L x y])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [label 0 -1 "$${{x^2} \\over {b^2}} + {{y^2} \\over {a^2}} = 1$$"]]))

(defn e2 []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx b' :ry a'}]
     [:circle {:r              b'
               :stroke-opacity 0.5
               :stroke         (:subselected default-theme)}]
     [:circle {:r              a'
               :stroke-opacity 0.5
               :stroke         "orange"}]
     [:g {:stroke-dasharray 0.1}
      [:rect {:x              (- b')
              :y              (- a')
              :width          (* b' 2)
              :height         (* a' 2)
              :stroke-opacity 0.5}]
      [:path {:d      (str/join " " ['M 0 0 'L b' 0])
              :stroke (:subselected default-theme)}]
      [:path {:d      (str/join " " ['M 0 0 'L 0 (- a')])
              :stroke "orange"}]]
     (let [x (* b' (math/cos @t))
           y (* a' (math/sin @t))]
       [:g
        [:path {:d (str/join " " ['M 0 0 'L x y])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [label (/ b' 2) 0 "$$b$$"]
     [label 0 (/ a' -3) "$$a$$"]]))

(defn e3 []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx b' :ry a'}]
     [:circle {:r              b'
               :stroke-opacity 0.5
               :stroke         (:subselected default-theme)}]
     [:circle {:r              a'
               :stroke-opacity 0.5
               :stroke         "orange"}]
     [:g {:stroke-dasharray 0.1}
      [:rect {:x              (- b')
              :y              (- a')
              :width          (* b' 2)
              :height         (* a' 2)
              :stroke-opacity 0.5}]
      [:path {:d      (str/join " " ['M 0 0 'L b' 0])
              :stroke (:subselected default-theme)}]
      [:path {:d      (str/join " " ['M 0 0 'L 0 (- a')])
              :stroke "orange"}]]
     (let [x (* b' (math/cos @t))
           y (* a' (math/sin @t))]
       [:g
        [:path {:d (str/join " " ['M 0 0 'L x y])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [label (/ b' 2) 0 "$$b$$"]
     [label 0 (/ a' -3) "$$a$$"]
     [label 2 1 "$$x = b\\cos\\phi$$"]
     [label 0 -3 "$$y = a\\sin\\phi$$"]]))

(defn ellipse-definition []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx b' :ry a'}]
     (let [x (* b' (math/cos @t))
           y (* a' (math/sin @t))]
       [:g
        [:path {:stroke "orange"
                :d      (str/join " " ['M 0 (- c') 'L x y 0 c'])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [:g {:stroke "green"
          :fill   "green"}
      [:circle {:cy (- c')
                :r  0.05}]
      [:circle {:cy c'
                :r  0.05}]]]))

(defn focal-points []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:ellipse {:rx b' :ry a'}]
     [:g {:stroke-opacity   0.5
          :stroke-dasharray 0.1}
      [:path {:d      (str/join " " ['M 0 (- c') 'L b' 0])
              :stroke "orange"}]
      [:path {:d      (str/join " " ['M 0 0 'L b' 0])
              :stroke (:subselected default-theme)}]
      [:path {:d      (str/join " " ['M 0 0 'L 0 (- c')])
              :stroke (:solid default-theme)}]
      [:path {:d      (str/join " " ['M 0 0 'L 0 a'])
              :stroke "orange"}]]
     (let [x (* b' (math/cos @t))
           y (* a' (math/sin @t))]
       [:g
        [:path {:stroke "orange"
                :d      (str/join " " ['M 0 (- c') 'L x y 0 c'])}]
        [:circle {:cx x
                  :cy y
                  :r  0.05}]])
     [label (/ b' 2) 0 "$$b$$"]
     [label (/ b' 2) (/ c' -2) "$$a$$"]
     [label 0 (/ c' -2) "$$c$$"]
     [label 0 -4.5 "$$a^2=b^2 + c^2$$"]
     [label 0 1.5 "$$c=\\sqrt {a^2 - b^2}$$"]
     [:g {:stroke "green"
          :fill   "green"}
      [:circle {:cy (- c')
                :r  0.05}]
      [:circle {:cy c'
                :r  0.05}]]]))

(defn isometric-triangle []
  (reagent/with-let [t (reagent/atom 0)]
    (when (< @t 1)
      (reagent/next-tick #(swap! t + 0.02)))
    (let [scale (+ 1 (* 2 (abs (math/sin @t))))]
      [:<>
       [:g {:transform    (str "translate(" 0 "," (+ -1 (math/sin @t))
                               ") rotate(" (+ 180 (* @t 60))
                               ") scale(" scale
                               ")")
            :stroke-width (/ 0.1 scale)}
        [isotri {}]]])))

(defn isometric-triangle2 []
  [:<>
   [:g {:transform    "scale(3)"
        :stroke-width 0.03}
    [:circle {:r              1
              :stroke-opacity 0.5}]
    [:path {:d (str/join " " ['M (/ r3 -2) (/ 1 2) 'L 0 0 'L (/ r3 2) (/ 1 2)])}]
    [:path {:d (str/join " " ['M (/ r3 -2) (/ 1 2) 'L 0 1 (/ r3 2) (/ 1 2)])}]
    [:path {:stroke-dasharray 0.05
            :stroke           "green"
            :d                (str/join " " ['M 0 (/ 1 2) 'L 0 1])}]
    [:path {:stroke (:solid default-theme)
            :d      (str/join " " ['M 0 -1 'L 0 (/ 1 2)])}]
    [:polygon {:stroke (:selected default-theme)
               :points (str/join " " [(/ r3 -2) (/ 1 2)
                                      (/ r3 2) (/ 1 2)
                                      0 -1])}]]
   [label 1 0.5 "$$1$$"]
   [label 0 -1 "$$h$$"]
   [label -2 4 "$$h = 1 {1 \\over 2}$$"]])

(defn isometric-triangle3 []
  (reagent/with-let [t (reagent/atom 0)]
    [:<>
     [:g {:transform    "scale(3)"
          :stroke-width 0.03}
      [:circle {:r              1
                :stroke-opacity 0.5}]
      [:path {:d (str/join " " ['M 0 (- 1) 'L 0 0])}]
      [:path {:d (str/join " " ['M 0 0 'L (/ r3 -2) (/ 1 2)])}]

      [:path {:stroke (:solid default-theme)
              :d      (str/join " " ['M (/ r3 -4) (/ 1 -4) 'L (/ r3 2) (/ 1 2)])}]
      [:path {:d (str/join " " ['M (/ r3 2) (/ 1 2) 'L (/ r3 2) (/ 1 -2) 0 -1])}]
      [:path {:stroke "orange"
              :d      (str/join " " ['M (/ r3 2) (/ 1 -2) 'L (/ r3 -2) (/ 1 2)])}]
      [:path {:d (str/join " " ['M (/ r3 2) (/ 1 2) 'L (/ r3 2) (/ 1 -2)])}]

      [:polygon {:stroke (:selected default-theme)
                 :points (str/join " " [(/ r3 -2) (/ 1 2)
                                        (/ r3 2) (/ 1 2)
                                        0 -1])}]]
     [label 0 -4 "$$1^2 + w^2 = 2^2$$"]
     [label 2 4 "$$w = \\sqrt 3$$"]
     [label -2 4 "$$h = 1 {1 \\over 2}$$"]
     [label 0 -1 "$$1$$"]
     [label 2.5 0 "$$1$$"]
     [label 2 -1 "$$2$$"]
     [label 0 1.5 "$$w$$"]]))

(def b (/ r3 r2))
(def a (/ 3 r2))
(def c (math/sqrt (- (math/pow a 2)
                     (math/pow b 2))))

(defn tangent-to-equilateral []
  ;; TODO: keep the point on the corner
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:g {:transform "translate(0,-1) rotate(180)"}
      [isotri {:r 1 :phi (/ math/PI 2)}]]
     [:path {:d (str/join " " ['M r3 0 'L 0 -3])}]
     [:ellipse {:rx     (+ b (* (- a b) (math/sin @t)))
                :ry     (- a (* (- a b) (math/sin @t)))
                :stroke (:selected default-theme)}]]))

(defn squares []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.005))
    [:<>
     [:g {:stroke-opacity (- 1 (min 0.5 @t))}
      [:polygon {:points (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]]
     [:g {:stroke-opacity (min 1 @t)}
      [:polygon {:points (str/join " " [(- r3) 0 0 (- r3) r3 0 0 r3])}]
      [:polygon {:points (str/join " " [-3 0 0 -3 3 0 0 3])}]]
     [:g {:stroke-opacity (max 0 (min 1 (- @t 1)))}
      [:circle {:r      (/ 3 r2)
                :stroke (:solid default-theme)}]
      [:circle {:r      (/ r3 r2)
                :stroke "orange"}]]
     [:g {:stroke-opacity (max 0 (min 1 (- @t 2)))}
      [:ellipse {:rx     (/ r3 r2)
                 :ry     (/ 3 r2)
                 :stroke (:selected default-theme)}]]]))

(defn elements []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:polygon {:stroke-opacity 0.5
                :points         (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]
     [:g {}
      [:rect {:stroke    "green"
              :transform "rotate(45)"
              :x         (/ (* 3 r2) -2)
              :y         (/ (* 3 r2) -2)
              :width     (* 3 r2)
              :height    (* 3 r2)}]
      [:rect {:stroke    "pink"
              :transform "rotate(45)"
              :x         (/ r3 r2 -1)
              :y         (/ r3 r2 -1)
              :width     (/ r3 r2 0.5)
              :height    (/ r3 r2 0.5)}]
      [:circle {:r      (/ 3 r2)
                :stroke (:solid default-theme)}]
      [:circle {:r      (/ r3 r2)
                :stroke "orange"}]]
     [:g {:transform      "translate(0,-1) rotate(180)"
          :stroke-opacity 0.5}
      [isotri {:r 1 :phi (/ math/PI 2)}]]
     [:ellipse {:rx     (/ r3 r2)
                :ry     (* (/ 3 2) r2)
                :stroke (:selected default-theme)}]
     [:g {:stroke-dasharray 0.1}
      [:path {:stroke "pink"
              :d      (str/join " " ['M 0 0 'L r3 0])}]
      [:path {:stroke "green"
              :d      (str/join " " ['M 0 0 'L 0 3])}]]]))

(defn show-focal-points []
  ;; TODO: keep the point on the corner
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:polygon {:points (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]
     [:g {:stroke-opacity 0.5}
      [:rect {:transform "rotate(45)"
              :x         (/ (* 3 r2) -2)
              :y         (/ (* 3 r2) -2)
              :width     (* 3 r2)
              :height    (* 3 r2)}]
      [:rect {:transform "rotate(45)"
              :x         (/ r3 r2 -1)
              :y         (/ r3 r2 -1)
              :width     (/ r3 r2 0.5)
              :height    (/ r3 r2 0.5)}]]
     [:path {:d (str/join " " ['M r3 0 'L 0 -3])}]
     [:ellipse {:rx     (/ r3 r2)
                :ry     (* (/ 3 2) r2)
                :stroke (:selected default-theme)}]
     [:g {:stroke (:solid default-theme)
          :fill   "green"}
      [:circle {:cy (- c)
                :r  0.1}]
      [:circle {:cy c
                :r  0.1}]]]))

(defn pythag-solution []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:polygon {:points (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]
     [:g {:stroke-opacity 0.5
          :stroke-width   0.2}
      [:rect {:stroke    "green"
              :transform "rotate(45)"
              :x         (/ (* 3 r2) -2)
              :y         (/ (* 3 r2) -2)
              :width     (* 3 r2)
              :height    (* 3 r2)}]
      [:rect {:stroke    "pink"
              :transform "rotate(45)"
              :x         (/ r3 r2 -1)
              :y         (/ r3 r2 -1)
              :width     (/ r3 r2 0.5)
              :height    (/ r3 r2 0.5)}]]
     [:g {:transform "translate(0,-1) rotate(180)"}
      [isotri {:r 1 :phi (/ math/PI 2)}]]
     [:ellipse {:rx     (/ r3 r2)
                :ry     (* (/ 3 2) r2)
                :stroke (:selected default-theme)}]
     [:g {:stroke "orange"}
      [:g {:stroke-width 0.2}
       [:path {:stroke "pink"
               :d      (str/join " " ['M 0 0 'L r3 0])}]
       [:path {:d (str/join " " ['M 0 0 (/ b r2) (/ b r2)])}]
       [:path {:d (str/join " " ['M r3 0 (/ b r2) (/ b r2)])}]]
      [:circle {:r b}]]
     [:g {:stroke (:solid default-theme)}
      [:g {:stroke-width 0.2}
       [:path {:d (str/join " " ['M 0 0 (/ a -1 r2) (/ a r2)])}]
       [:path {:d (str/join " " ['M 0 3 (/ a -1 r2) (/ a r2)])}]
       [:path {:stroke "green"
               :d      (str/join " " ['M 0 0 'L 0 3])}]]
      [:circle {:r a}]]
     [label (/ 1 2) (/ 1 2) "$$b$$"]
     [label (/ 3 2) (/ 1 2) "$$b$$"]
     [label (/ 1 -2) (/ 1 2) "$$a$$"]
     [label (/ 1 -2) (/ 5 2) "$$a$$"]
     [label 0 -4 "$$b^2 + b^2 = {\\sqrt 3}^2$$"]
     [label 2 -2 "$$b = {\\sqrt {3 \\over 2}} = {{\\sqrt 3} \\over {\\sqrt 2}}$$"]
     [label 3 2 "$$a^2 + a^2 = 3^2$$"]
     [label 0 4 "$$a = \\sqrt {9 \\over 2} = {3 \\over \\sqrt 2}$$"]]))

(defn variable-a-b []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    (let [b (* (/ 3 2) r2 (abs (math/cos @t)))
          c (math/sqrt (abs (- (math/pow a 2)
                               (math/pow b 2))))
          x (math/sqrt (* 2 (math/pow b 2)))]
      [:<>
       [:polygon {:points (str/join " " [(- x) 0
                                         0 3
                                         x 0
                                         0 -3])}]
       [:g {:stroke-opacity 0.5}
        [:rect {:transform "rotate(45)"
                :x         (- a)
                :y         (- a)
                :width     (* a 2)
                :height    (* a 2)}]
        [:rect {:transform "rotate(45)"
                :x         (- b)
                :y         (- b)
                :width     (* b 2)
                :height    (* b 2)}]]
       [:ellipse {:rx     b
                  :ry     a
                  :stroke (:selected default-theme)}]
       [:g {:stroke (:solid default-theme)
            :fill   "green"}
        [:circle {:cx (when (> b a)
                        (- c))
                  :cy (when (< b a)
                        (- c))
                  :r  0.1}]
        [:circle {:cx (when (> b a)
                        c)
                  :cy (when (< b a)
                        c)
                  :r  0.1}]]])))

(defn rotating-boxes []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    (let [b (* (/ 3 2) r2 (- 1 (math/sin @t)))
          c (math/sqrt (abs (- (math/pow a 2)
                               (math/pow b 2))))
          x (math/sqrt (* 2 (math/pow b 2)))]
      [:<>
       [:g {:transform (str "rotate(" (- 125 (* 180 (math/sin @t))) ")")}
        [:polygon {:points (str/join " " [(- x) 0
                                          0 3
                                          x 0
                                          0 -3])}]
        [:g {:stroke-opacity 0.5}
         [:rect {:transform "rotate(45)"
                 :x         (- a)
                 :y         (- a)
                 :width     (* a 2)
                 :height    (* a 2)}]
         [:rect {:transform "rotate(45)"
                 :x         (- b)
                 :y         (- b)
                 :width     (* b 2)
                 :height    (* b 2)}]]
        [:ellipse {:rx     b
                   :ry     a
                   :stroke (:selected default-theme)}]
        [:g {:stroke (:solid default-theme)
             :fill   "green"}
         [:circle {:cx (when (> b a)
                         (- c))
                   :cy (when (< b a)
                         (- c))
                   :r  0.1}]
         [:circle {:cx (when (> b a)
                         c)
                   :cy (when (< b a)
                         c)
                   :r  0.1}]]]])))

(defn rotating-circles []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    (let [b (* (/ 3 2) r2 (+ 1 (math/sin @t)))
          c (math/sqrt (abs (- (math/pow a 2)
                               (math/pow b 2))))]
      [:<>
       [:g {:transform (str "rotate(" (* 180 (math/sin @t)) ")")}
        [:g {:stroke-opacity 0.5}
         [:circle {:r a}]
         [:circle {:r b}]]
        [:ellipse {:rx     b
                   :ry     a
                   :stroke (:selected default-theme)}]
        (let [x (* b (math/cos @t))
              y (* a (math/sin @t))]
          [:g
           [:path {:stroke "orange"
                   :d      (str/join " " ['M (if (> b a) (- c) 0)
                                          (if (> b a) 0 (- c))
                                          'L x y
                                          (if (> b a) c 0)
                                          (if (> b a) 0 c)])}]
           [:circle {:cx x
                     :cy y
                     :r  0.05}]])
        [:g {:stroke (:solid default-theme)
             :fill   "green"}
         [:circle {:cx (when (> b a)
                         (- c))
                   :cy (when (< b a)
                         (- c))
                   :r  0.1}]
         [:circle {:cx (when (> b a)
                         c)
                   :cy (when (< b a)
                         c)
                   :r  0.1}]]]])))

(defn skewed []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:g {:transform "rotate(45)"}
      [:polygon {:points (str/join " " [(- r3) 0 0 3 r3 0 0 -3])}]
      [:g {:stroke-opacity 0.5
           }
       [:rect {:transform "rotate(45)"
               :x         (/ (* 3 r2) -2)
               :y         (/ (* 3 r2) -2)
               :width     (* 3 r2)
               :height    (* 3 r2)}]
       [:rect {:transform "rotate(45)"
               :x         (/ r3 r2 -1)
               :y         (/ r3 r2 -1)
               :width     (/ r3 r2 0.5)
               :height    (/ r3 r2 0.5)}]]
      [:path {:d (str/join " " ['M r3 0 'L 0 -3])}]
      [:ellipse {:rx     (/ r3 r2)
                 :ry     (* (/ 3 2) r2)
                 :stroke (:selected default-theme)}]]]))

(defn point-and-ratio []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [:polygon {:points (str/join " " [r3 0
                                       (- r3) 0
                                       0 -3])}]
     [:g {:transform "translate(0,-1) rotate(180)"}
      [isotri {:r 1 :phi (/ math/PI 2)}]]
     [:g {:stroke (:selected default-theme)}
      [:ellipse {:rx b :ry a}]]
     [:g {:stroke "orange"}
      [:line {:x1 0 :y1 0
              :x2 0 :y2 -3}]
      [:line {:x1 0 :y1 0
              :x2 r3 :y2 0}]]
     [label 0 -1.5 "$$3$$"]
     [label (/ r3 2) 0 "$$\\sqrt 3$$"]
     [label 2.5 -2 "$$x,y = {\\sqrt 3 \\over 2},{3 \\over 2}$$"]
     [label -2 4 "$${a \\over b} = {3 \\over {\\sqrt 3}}$$"]
     [label 2 4 "$$a = b {\\sqrt 3}$$"]
     [:circle {:stroke "red"
               :fill   "red"
               :cx     (/ r3 2)
               :cy     (/ 3 -2)
               :r      0.04}]]))

(defn substituting []
  (reagent/with-let [t (reagent/atom 0)]
    (reagent/next-tick #(swap! t + 0.01))
    [:<>
     [label 0 -4 "$${x^2\\over b^2} + {y^2\\over a^2} = 1 = {x^2\\over b^2} + {y^2\\over 3b^2}$$"]
     [label 0 -2 "$$b^2 = x^2 + {y^2 \\over 3}$$"]
     [label 0 0 "$$b^2 = {({{\\sqrt 3}\\over 2})^2} + {({3\\over 2})^2\\over 3}$$"]
     [label 0 2 "$$b^2 = {({3\\over 4})} + {({9\\over 4})\\over 3} = {3\\over 2}$$"]
     [:rect {:stroke         "orange"
             :stroke-opacity 0.5
             :x              -4
             :y              3
             :width          8
             :height         1.96}]
     [label -2 4 "$$b = {\\sqrt 3 \\over \\sqrt 2}$$"]
     [label 2 4 "$$a = {3 \\over {\\sqrt 2}}$$"]]))

(def previews
  [ellipse-definition
   focal-points
   isometric-triangle2
   isometric-triangle3
   honeycomb
   squares
   elements
   pythag-solution
   variable-a-b
   rotating-boxes
   point-and-ratio
   rotating-circles])

(defn overview []
  (let [dphi (/ (* 2 math/PI) (count previews))]
    (into
      [:<>
       [fo {:x     0
            :y     -30
            :scale 0.2}
        [md/md {:style {:color (:text default-theme)}}
         "# The Eccentric Ellipse"]]
       [:image {:href   "/img/hummi.svg"
                :x      -10
                :y      -10
                :width  20
                :height 20}]]
      (map-indexed
        (fn [idx component]
          [g2 {}
           (* 30 (math/cos (* idx dphi)))
           (* 20 (math/sin (* idx dphi)))
           [component]])
        previews))))

(def ggg
  [:document {}
   overview
   [:section {}
    goal
    tangent-to-equilateral
    squash-circle
    ellipse-definition
    e2
    ellipse
    circle
    e3
    focal-points]

   ;; What are the dimensions of an equilateral triangle?
   [:section {}
    goal
    isometric-triangle
    isometric-triangle2
    isometric-triangle3
    hexagons
    honeycomb]

   ;; Constructing a special ellipse
   [:section {}
    goal
    squares
    elements
    pythag-solution]

   ;; Focal points
   [:section {}
    show-focal-points
    variable-a-b
    rotating-boxes
    skewed]

   ;; Checking
   [:section {}
    point-and-ratio
    substituting
    rotating-circles]])

;; TODO: need a way to go up? (could just go left until a tag?)
;; or maybe annotate the tree instead?
(def dfs
  (->> (tree-seq vector? nnext ggg)
       (remove vector?)
       (vec)))

(defn zoomable [{:keys [wheel keydown]} & body]
  (reagent/create-class
    {:display-name "zoomable"
     :component-did-mount
     (fn [this]
       (when keydown
         (js/window.addEventListener "keydown" keydown #js{:passive false}))
       (when wheel
         (js/window.addEventListener "wheel" wheel #js{:passive false})))
     :component-will-unmount
     (fn [this]
       (when wheel
         (js/window.removeEventListener "wheel" wheel #js{:passive false}))
       (when keydown
         (js/window.removeEventListener "keydown" keydown #js{:passive false})))
     :reagent-render
     (fn [props & body]
       (into [:div (dissoc props :wheel :keydown)] body))}))

#_(defn tree-get-in [tree path]
    {:pre [(vector? tree)
           (every? int? path)]}
    (reduce #(get tree (+ % 2)) tree path))

#_(defn gget [path]
    (tree-get-in ggg path))

#_(fn [path]
    (let [right (inc-last path)
          down (conj path 0)
          up-right (conj (pop path) 0)]
      (cond
        (gget down) down
        (gget right) right
        (gget up-right) up-right
        :else [0])))
#_(fn [path]
    (let [left-down ()
          left ()
          up ()]))

(defn svg-narrative [props]
  (reagent/with-let [v (reagent/atom {:x      -40
                                      :y      -40
                                      :width  80
                                      :height 80})
                     va (anim/spring v anim/integrate-map {})]
    (let [{:keys [idx]} props
          component (nth dfs idx)
          vb1 {:x      -40
               :y      -40
               :width  80
               :height 80}
          vb2 {:x      -5
               :y      -5
               :width  10
               :height 10}]
      (if (zero? idx)
        (when (not= @v vb1)
          (reset! v vb1))
        (when (not= @v vb2)
          (reset! v vb2)))
      [svg {:viewBox ((juxt :x :y :width :height) @va)}
       [component]])))

(defonce app-state
  (reagent/atom {:idx 0}))

#_(defn inc-last [path]
    (let [last-idx (dec (count path))
          last-val (peek path)]
      (assoc path last-idx last-val)))

(defn limit-idx [idx]
  (mod idx (count dfs)))

(def forward* (comp limit-idx inc))

(def back* (comp limit-idx dec))

(defn sup! [path f & args]
  (apply swap! app-state update-in path f args))

(defn forward []
  (sup! [:idx] forward*))

(defn back []
  (sup! [:idx] back*))

(def actions
  {:forward forward
   :back    back})

(defn app []
  (reagent/with-let [on-keydown (fn [ev]
                                  (condp = (.-key ev)
                                    k/SPACE (forward)
                                    k/RIGHT (forward)
                                    k/LEFT (back)
                                    k/BACKSPACE (back)
                                    nil))]
    [zoomable {:keydown on-keydown
               :onClick (fn [ev]
                          (forward))
               :style   {:background (:solid default-theme)}}
     [:br]
     [ui/container {:text true}
      [svg-narrative (conj @app-state actions)]]
     [:br]]))
