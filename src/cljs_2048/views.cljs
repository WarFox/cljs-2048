(ns cljs-2048.views
  (:require
   [cljs-2048.events :as events]
   [cljs-2048.styles :as styles]
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
(defn tile-colours
  [value]
  (case value
    0         "#BBBBBB"
    2         "#7fbfc7"
    4         "#82b4c2"
    8         "#86aabc"
    16        "#899fb7"
    32        "#8c95b1"
    64        "#908aac"
    128       "#937fa7"
    256       "#9675a1"
    512       "#996a9c"
    1024      "#9d5f97"
    2048      "#a05591"
    4096      "#a34a8c"
    8192      "#a74086"
    "default" "#aa3581"))

(defn tile-panel
  [index value]
  ^{:key index}
  [:div {:class (styles/board-tile (tile-colours value))}
   (if (zero? value) "" value)])

;; Panel used to show Score and Best score
(defn score-panel
  [header value]
  [:div.score
   [:h3 header ": " value]])

(defn board-panel
  "Rows and columns display of current board"
  []
  (let [board (re-frame/subscribe [::subs/board])] ;; Get the current state of the board
    [:div {:class (styles/board)}
     ;; [:pre (with-out-str (cljs.pprint/pprint @board))] ;; print the board in page for debugging
     (map-indexed ;; Each row of the board
      (fn [index value]
        ^{:key index}
        [:div {:class (styles/board-row)}
         (map-indexed ;; Each tile of the row
          tile-panel
          value)])
      @board)
     [:br.clear]]))

(defn gameover-panel []
  [:div
   "Game Over"])

(defn display-game
  []
  (let [score (re-frame/subscribe [::subs/score])
        high-score (re-frame/subscribe [::subs/high-score])
        gameover (re-frame/subscribe [::subs/gameover])]
    [:div#game-panel
     (score-panel "Score" @score)
     (score-panel "High Score" @high-score)
     (when @gameover
       (gameover-panel))
     [:button {:on-click #(re-frame/dispatch [::events/start-game])} "New Game"]
     [:br.clear]
     (board-panel)]))

(defn main-panel
  []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 @name]
     [display-game]]))
