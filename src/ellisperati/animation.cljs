(ns ellisperati.animation
  (:require [clojure.data :as data]
            [reagent.ratom :as ratom]
            [reagent.core :as reagent]))

(defn ^:private evaluate
  "This is where the spring physics formula is applied."
  [x2 dt x v a {:keys [mass stiffness damping]
                :or   {mass      1.0
                       stiffness 2.0
                       damping   3.0}}]
  (let [x (+ x (* v dt))
        v (+ v (* a dt))
        f (- (* stiffness (- x2 x)) (* damping v))
        a (/ f mass)]
    [v a]))

(defn ^:private integrate-rk4
  "Takes an itegration step from numbers x to x2 over time dt,
  with a present velocity v."
  [x2 dt x v options]
  (let [dt2 (* dt 0.5)
        [av aa] (evaluate x2 0.0 x v 0.0 options)
        [bv ba] (evaluate x2 dt2 x av aa options)
        [cv ca] (evaluate x2 dt2 x bv ba options)
        [dv da] (evaluate x2 dt x cv ca options)
        dx (/ (+ av (* 2.0 (+ bv cv)) dv) 6.0)
        dv (/ (+ aa (* 2.0 (+ ba ca)) da) 6.0)]
    [(+ x (* dx dt)) (+ v (* dv dt))]))

(defn integrate-map [goal diff dt current velocity options]
  (reduce (fn [[acc nvs] [k x]]
            (let [current-x (or (get current k) x)
                  v (or (get velocity k) 0)
                  [next-x next-v] (integrate-rk4 x dt current-x v options)]
              [(assoc acc k next-x) (assoc nvs k next-v)]))
          [goal velocity]
          (second diff)))

(defn spring
  "Useful for wrapping a value in your component to make it springy.
  Returns a reaction that will take values approaching x2,
  updating every time Reagent calls requestAnimationFrame.
  Integrates a physical spring simulation for each step.
  Options can contain:
  from - a value to start from (initial value is used if absent).
  velocity of the mass on the spring (initially 0 if absent).
  mass, stiffness, damping of the spring."
  [goal integrate-fn options]
  (let [prev-goal (atom @goal)
        anim (reagent/atom nil)]
    (ratom/reaction
      (let [t2 (.getTime (js/Date.))
            c @prev-goal
            g @goal]
        (when (not= c g)
          (if (-> g meta :now)
            (reset! anim nil)
            (reset! anim {:start    (- t2 20)               ;; start in the past so animate begins now
                          :t        (- t2 20)
                          :current  c
                          :goal     g
                          :diff     (data/diff c g)
                          :velocity {}}))
          (reset! prev-goal g))
        (if @anim
          (let [{:keys [current goal diff velocity t start]} @anim
                dt 0.1]
            (if (> (- t2 start) 2000.0)
              (do (reset! anim nil) g)
              (let [[intermediate velocity] (integrate-fn goal diff dt current velocity options)]
                (reagent/next-tick #(reset! anim {:start    start
                                                  :t        t2
                                                  :current  intermediate
                                                  :goal     goal
                                                  :diff     diff
                                                  :velocity velocity}))
                intermediate)))
          g)))))
