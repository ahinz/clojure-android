(require 'clojure.java.io)
(defn- load-props
  [file-name]
  (with-open [^java.io.Reader reader (clojure.java.io/reader file-name)] 
    (let [props (java.util.Properties.)]
      (.load props reader)
      (into {} (for [[k v] props] [(keyword k) v])))))

(def android-cp
  (let [sdk (:sdk.dir (load-props "local.properties"))
        android-ver (:target (load-props "project.properties"))
        path "%s/platforms/%s/android.jar"]
    [(format path sdk android-ver)]))

(defproject factorial "0.1.0-SNAPSHOT"
  :description "A simple factorial library"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :java-source-paths ["src/fac", "gen"]
  :source-paths ["src/clojure"]
  :compile-path "bin/classes"
  :dependencies [[android/clojure "1.5.0"]]
  :main android.factorial.activity
  :aot :all
  :resource-paths ~android-cp
  :aot-exclude-ns ["clojure.parallel"]
  :exclusions [[orgp.clojure/clojure]])
