(ns todo-clj.core
  (:require [compojure.core :refer [routes]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :as server]
            [ring.middleware.resource :as resource]
            [todo-clj.handler.main :refer [main-routes]]
            [todo-clj.handler.todo :refer [todo-routes]]
            [todo-clj.middleware :refer [wrap-dev]]
            ))

(defn- wrap [handler middleware opt]
  (if (true? opt)
    (middleware handler)
    (if opt
      (middleware handler opt)
      (handler))))

(def app
  (-> (routes
       todo-routes
       main-routes)
      (wrap wrap-dev (read-string (:dev env)))
      (wrap resource/wrap-resource "public")))

(defonce server (atom nil))

(defn start-server []
  (swap! server #(when-not % (server/run-jetty #'app {:port 3000 :join? false}))))

(defn stop-server []
  (swap! server
         (fn [s]
           (when s
             (.stop s)
             nil))))

(defn restart-server []
  (stop-server)
  (start-server))
