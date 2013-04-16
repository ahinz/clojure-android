(ns android.factorial.core
  (:gen-class)
  (:require 
   [cheshire.core :as json])
  (:import
   (com.google.android.gms.maps GoogleMap CameraUpdateFactory)
   (com.google.android.gms.maps.model Marker MarkerOptions LatLng)
   (org.jsoup Jsoup)
   (org.jsoup.nodes Document)))

(set! *warn-on-reflection* true)

(defn- parse [^String url]
  (.get (Jsoup/connect url)))

(defn read-vendors-page []
  (let [^Document doc (parse "http://vegphilly.com/vendors/")
        ^String content
        (.html
         (.get
          (.select 
           doc
           "script") 5))

        json-blob (.trim
                   (.substring content
                               (.indexOf content "[")
                               (.lastIndexOf content "var")))
        json-blob (.substring json-blob 0 (- (count json-blob) 1))
        ;; The keys are in js format:
        ;; akey: "value"
        ;; but need to be in:
        ;; "akey": "value" format
        json-blob (.replaceAll json-blob "(\\s+)([A-Za-z0-9]+)(:.*)" "$1\"$2\"$3")]    
    (json/parse-string json-blob)))

(defn add-marker-to-map [^GoogleMap map lat lng]
  (.addMarker 
   map
   (-> (MarkerOptions.)
       (.position (LatLng. (java.lang.Double/parseDouble lat)
                           (java.lang.Double/parseDouble lng)))
       (.title "Title"))))

(defn init-map [^GoogleMap map]
  ; Center
  (.moveCamera map (CameraUpdateFactory/newLatLng (LatLng. 39.977 -75.154)))
  (.moveCamera map (CameraUpdateFactory/zoomTo 12))

  ; Add markers
  (doseq [vendor (read-vendors-page)]
    (add-marker-to-map map (get vendor "latitude") (get vendor "longitude"))))

