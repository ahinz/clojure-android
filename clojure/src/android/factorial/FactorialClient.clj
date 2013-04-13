(ns fac.android)

(gen-class :name fac.android.FactorialActivity
           :extends android.app.Activity
           :main false
           :prefix "fac-"
           :exposes-methods {onCreate onCreate})

(defn fac-on-create [bundle]
  (.onCreate this bundle)
  (.setContentView this (.main R/layout)))
