(defproject todo-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Apache License Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :profiles {:dev
             {:dependencies [[prone "2019-07-08"]]
              :env {:dev true}}}
  :repl-options {:init-ns todo-clj.core})
