(ns android.factorial.activity
  (:require [android.factorial.core :as core])
  (:import 
   (fac.android R$id R$layout)
   (android.util Log)))

;(set! *warn-on-reflection* true)

(gen-class :name fac.android.FactorialActivity
           :extends android.app.Activity
           :main false
           :prefix "-"
           :exposes-methods {onCreate superOnCreate})

(defn -onCreate [this bundle]
  (Log/i "Factorial" "Called this....")
  (.superOnCreate this bundle)
  (.setContentView this R$layout/main)
  (let [button (.findViewById this R$id/bt)
        text (.findViewById this R$id/tx)]
    (.setOnClickListener button
     (reify android.view.View$OnClickListener
       (onClick [this view]
         (.setText text "The button was clicked")))))

  (let [map (-> this 
                .getFragmentManager
                (.findFragmentById R$id/map)
                .getMap)]
    (core/init-map map)))
