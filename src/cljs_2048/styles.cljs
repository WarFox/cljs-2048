(ns cljs-2048.styles
  (:require
   [goog.dom :as gdom]))

(defn inject-node! [old-node new-node document]
  (if old-node
    (gdom/replaceNode new-node old-node)
    (gdom/appendChild (.-head document) new-node)))

(defn inject-inline-link [document id link]
  (let [old-link (gdom/getElement id)
        new-link (gdom/createDom "link"
                                 (clj->js {:id id
                                           :rel :stylesheet
                                           :href link}))]
    (inject-node! old-link new-link document)))


(defn inject-trace-styles [document]
  (inject-inline-link document "--app.css--"  "app.css"))
