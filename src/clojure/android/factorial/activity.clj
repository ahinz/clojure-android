(ns android.factorial.activity
  (:require [android.factorial.core :as core])
  (:import 
   (fac.android R$id R$layout ListActivity)
   (android.content Intent)
   (android.util Log)))

(set! *warn-on-reflection* true)

(gen-class :name fac.android.FactorialActivity
           :extends android.app.Activity
           :main false
           :prefix "-"
           :exposes-methods {onCreate superOnCreate})

(defn -onCreate [^android.app.Activity this bundle]
  (Log/i "Factorial" "Called this....")
  (.superOnCreate this bundle)
  (.setContentView this R$layout/main)
  (let [button (.findViewById this R$id/bt)
        ^android.widget.TextView text (.findViewById this R$id/tx)]
    (.setOnClickListener button
     (reify android.view.View$OnClickListener
       (onClick [_ view]
         (.setText text "The button was clicked")
         (.startActivityForResult this (Intent. (.getContext view) ListActivity) 0)))))

  (let [map (-> this 
                .getFragmentManager
                (.findFragmentById R$id/map)
                .getMap)]
    (core/init-map map)))
