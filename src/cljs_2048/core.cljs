(ns cljs-2048.core
  (:require
   [cljs-2048.config :as config]
   [cljs-2048.events :as events]
   [cljs-2048.views :as views]
   [cljs-2048.styles :as styles]
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]
   [reagent.dom :as rdom]))

(defn dev-setup
  []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root
  []
  (re-frame/clear-subscription-cache!)
  (styles/inject-trace-styles js/document)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init
  []
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])
  (re-frame/dispatch-sync [::events/start-game])
  (views/dispatch-keydown-rules)
  (dev-setup)
  (mount-root))
