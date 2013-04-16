(ns android.factorial.list_activity
  (:require 
   [android.factorial.core :as core]
   [android.factorial.list :as list])
  (:import 
   (fac.android R$id R$layout)
   (android.util Log)))

(set! *warn-on-reflection* true)

(gen-class :name fac.android.ListActivity
           :extends android.app.Activity
           :main false
           :prefix "-"
           :exposes-methods {onCreate superOnCreate})

(defn -onCreate [this bundle]
  (Log/i "Factorial" "Called this.... (LIST VIEW)")
  (.superOnCreate this bundle)
  (.setContentView this R$layout/list)
  (let [^android.widget.ListView listview (.findViewById this R$id/listview)
        adapter (list/create-map-adapter this (core/read-vendors-page) R$layout/cell str)]
    (.setAdapter listview adapter)))
