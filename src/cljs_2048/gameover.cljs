(ns cljs-2048.gameover
  (:require
   [cljs-2048.events :as events]
   [re-frame.core :as re-frame]))

(def view
  [:div {:class "relative flex justify-center items-center z-20"}
   [:div {:class "w-full h-full bg-gray-900 bg-opacity-80 top-0 fixed sticky-0"}
    [:div {:class "2xl:container  2xl:mx-auto py-48 px-4 md:px-28 flex justify-center items-center"}
     [:div {:class "w-96 md:w-auto dark:bg-gray-800 relative flex flex-col justify-center items-center bg-white py-16 px-4 md:px-24 xl:py-24 xl:px-36"}
      [:div {:class "mt-12"}
       [:h1 {:role "main" :class "text-3xl dark:text-white lg:text-4xl font-semibold text-center text-gray-800"} "Game Over!!!"]]
      [:div {:class "mt"}
       [:p {:class "mt-6 sm:w-80 text-base dark:text-white text-center text-gray-800"} "What an amazing attempt, well done!"]]

      [:button.btn-primary {:on-click #(re-frame/dispatch [::events/start-game])} "New Game"]
      ]]]])
