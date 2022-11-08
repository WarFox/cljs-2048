(ns cljs-2048.views
  (:require
   [cljs-2048.events :as events]
   [cljs-2048.gameover :as gameover]
   [cljs-2048.subs :as subs]
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]))

(defn dispatch-keydown-rules
  []
  (re-frame/dispatch
   [::rp/set-keydown-rules
    {:event-keys [[[::events/move-left]
                   [{:keyCode 37}]] ;; lef-arrow

                  [[::events/move-up]
                   [{:keyCode 38}]] ;; up-arrow

                  [[::events/move-right]
                   [{:keyCode 39}]] ;; lef-arrow

                  [[::events/move-down]
                   [{:keyCode 40}]] ;; down-arrow
                  ]
     :clear-keys
     [[{:keyCode 27} ;; escape
       ]]}]))

;; Displaying the game tile
(defn tile-panel
  [index value]
  ^{:key index}
  [:div {:class (str "tile tile-" value)}
   (if (zero? value) "" value)])

;; Panel used to show Score and Best score
(defn score-panel
  [type value]
  (let [[header class] (if (= type ::high-score)
                         ["High Score" "score score-high"]
                         ["Score" "score"])]
    [:div {:class class
           :aria-orientation "vertical" :aria-labelledby "mobile-menu-button"}
     [:span {:class "block px-4 py-2 text-lg text-gray-700"}
      [:h3 header]
      value]]))

(defn board-panel
  "Rows and columns display of current board"
  []
  (let [board (re-frame/subscribe [::subs/board])] ;; Get the current state of the board
    [:div.board
     ;; [:pre (with-out-str (cljs.pprint/pprint @board))] ;; print the board in page for debugging
     (map ;; Each row of the board
      (fn [value]
        [:div.row
         (map-indexed ;; Each tile of the row
          tile-panel
          value)])
      @board)
     [:br.clear]]))

(defn gameover-panel []
  [:div gameover/view])

(defn score
  []
  (let [score (re-frame/subscribe [::subs/score])
        high-score (re-frame/subscribe [::subs/high-score])]
    [:div.flex.flex-row-reverse
     (score-panel ::high-score @high-score)
     (score-panel ::score @score)]))

(defn game-panel
  []
  (let [gameover (re-frame/subscribe [::subs/gameover])]
    [:div.flex.flex-col.items-center
     (when @gameover (gameover-panel))
     [:button.btn-primary.bg-blue-200 {:on-click #(re-frame/dispatch [::events/start-game])} "New Game"]
     [board-panel]]))

(defn header
  [name]
  [:h2.header name])

(defn main-panel
  []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div.container.mx-auto.center
     (header @name)
     [score]
     [game-panel]]))
