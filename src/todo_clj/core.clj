(ns todo-clj.core
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as server]
            [ring.util.response :as res]))

; api
(defn html [res]
  (res/content-typ res "text/html; charset=utf-8"))

(defn home-view [req]
  "<h1>ホーム画面</h1>
   <a href=\"/todo\">TODO 一覧</a>")

(defn home [req]
  (-> (home-view req)
      res/response
      html))

(def todo-list
  [{:title "朝ごはんを作る"}
   {:title "エサをあげる"}
   {:title "水を入れ替える"}
   {:title "トイレを綺麗にする"}])

(defn todo-index-view [req]
  `("<h1>TODO 一覧</h1>"
    "<ul>"
    ~@(for [{:keys [title]} todo-list]
        (str "<li>" title "</li>"))
    "</ul>"))

(defn todo-index [req]
  (-> (todo-index-view req)
      res/response
      html))

(defroutes handler
  (GET "/" req home)
  (GET "/todo" req todo-index)
  (route/not-found "<h1>404 page not found</h1>"))


; server
(defonce server (atom nil))

(defn start-server []
  (swap! server #(when-not % (server/run-jetty #'handler {:port 3000 :join? false}))))

(defn stop-server []
  (swap! server
         (fn [s] (when @server
                   (.stop @server)
                   (reset! server nil)))))

(defn restart-server []
  (stop-server)
  (start-server))
