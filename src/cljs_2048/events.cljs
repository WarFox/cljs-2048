(ns cljs-2048.events
  (:require
   [re-frame.core :as re-frame]
   [cljs-2048.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 ::set-re-pressed-example
 (fn [db [_ value]]
   (assoc db :re-pressed-example value)))

(re-frame/reg-event-db
 ::start-game
 (fn-traced [db [_ _]]
            (-> db
                (assoc :started true))))
