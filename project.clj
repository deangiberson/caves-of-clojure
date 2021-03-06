(defproject caves-of-clojure "0.1.0-SNAPSHOT"
  :description "The Caves of Clojure"
  :url "http://stevelosh.com/blog/2012/07/caves-of-clojure-01/"
  :license {:name "MIT/X11"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clojure-lanterna "0.9.3"]]
  :repositories {"sonatype-snapshots" "https://oss.sonatype.org/content/repositories/snapshots"}
  :main caves_of_clojure.core
  :plugins [[lein-kibit "0.0.8"]])
