(ns android.factorial.list
  (:gen-class)
  (:require 
   [cheshire.core :as json])
  (:import
   (fac.android R$drawable)
   (android.content Context)
   (com.google.android.gms.maps GoogleMap CameraUpdateFactory)
   (com.google.android.gms.maps.model Marker MarkerOptions LatLng BitmapDescriptorFactory)
   (org.jsoup Jsoup)
   (org.jsoup.nodes Document)))

(set! *warn-on-reflection* true)

(defn- get-inflater ^android.view.LayoutInflater [^Context ctxt]
  (.getSystemService ctxt Context/LAYOUT_INFLATER_SERVICE))

(defn- inflate-or-return [ctxt
                          ^android.view.View convertView 
                          ^android.view.ViewGroup parent
                          ^Integer resource]
  (or convertView
      (.inflate (get-inflater ctxt) resource parent false)))

(defn- mk-view [ctxt display convertView parent resource fieldId]
  (let [view (inflate-or-return ctxt convertView parent resource)
        text (if fieldId
               (.findViewById view fieldId)
               view)]
    (.setText text (str display))
    view))

(defn create-map-adapter [ctxt aseq resource as-string]
  (reify
    android.widget.Adapter
    (getCount [this] (count aseq))
    (getItem [this pos] (nth aseq pos))
    (getItemId [this pos] pos)
    (getItemViewType [this pos] 0)
    (getView [this pos convert parent] 
      (mk-view 
       ctxt (as-string (nth aseq pos)) convert parent resource nil))
    (getViewTypeCount [this] 1)
    (hasStableIds [this] true)
    (isEmpty [this] (empty? aseq))
    (registerDataSetObserver [this obs])
    (unregisterDataSetObserver [this obs])
    android.widget.ListAdapter
    (areAllItemsEnabled [this] true)
    (isEnabled [this pos] true)))
