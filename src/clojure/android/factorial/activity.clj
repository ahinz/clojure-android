(ns android.factorial.activity
  (:import (fac.android R$id R$layout)
           (android.util Log)))

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
         (.setText text "The button was clicked"))))))
