(ns cljs-2048.game-test
  (:require
   [cljs-2048.board :as board]
   [cljs-2048.game :as sut]
   [cljs.test :refer-macros [deftest testing is]]))

(deftest can-move-sideways?-test
  (is (true? (sut/can-move-sideways? [2 2 3 4])))
  (is (true? (sut/can-move-sideways? [1 3 3 4])))
  (is (true? (sut/can-move-sideways? [1 2 4 4])))
  (is (true? (sut/can-move-sideways? [2 2 2 4])))
  (is (true? (sut/can-move-sideways? [1 2 2 2])))
  (is (nil? (sut/can-move-sideways? [1 2 3 4]))))

(def can-move-sideways [[2 8 2 4]
                        [4 2 4 8]
                        [2 8 2 2]
                        [4 2 4 8]])

(def can-move-up-or-down [[2 8 2 4]
                          [4 2 4 8]
                          [2 8 2 4]
                          [4 8 4 8]])

(deftest can-move-test
  (is (true? (sut/can-move? can-move-sideways)))
  (is (true? (sut/can-move? can-move-up-or-down))))

(deftest gameover?-test
  (testing "game not over, when there can be move to left"
    (is (false? (sut/gameover? can-move-sideways))))

  (testing "game not over, when there can be move up"
    (is (false? (sut/gameover? can-move-up-or-down))))

  (testing "game over, when there can be no more moves"
    (is (true? (sut/gameover? [[2 4 2 4]
                               [4 2 4 2]
                               [2 4 2 4]
                               [4 2 4 2]])))))

(def test-board-2
  [[1 2 3 4]
   [0 6 7 8]
   [0 0 11 12]
   [0 0 0 16]])

(deftest move-left-test
  (is (= (sut/move-left test-board-2)
         [[1 2 3 4]
          [6 7 8 0]
          [11 12 0 0]
          [16 0 0 0]])))

(deftest move-right-test
  (is (= (sut/move-right (sut/move-left test-board-2))
         test-board-2)))

(deftest move-down-test
  (is (= (sut/move-down test-board-2)
         [[0 0 0 4]
          [0 0 3 8]
          [0 2 7 12]
          [1 6 11 16]])))

(deftest move-up-test
  (is (= (sut/move-up (sut/move-up test-board-2))
         test-board-2)))

(def test-board-3
  [[0 0 0 8]
   [0 2 2 8]
   [4 4 8 8]
   [0 16 8 16]])

(deftest move-left-and-combine
  (is (= (sut/move-left test-board-3)
         [[8 0 0 0]
          [4 8 0 0]
          [8 16 0 0]
          [16 8 16 0]])))

(deftest move-right-and-combine
  (is (= (sut/move-right test-board-3)
         [[0 0 0 8]
          [0 0 4 8]
          [0 0 8 16]
          [0 16 8 16]])))

(deftest move-up-and-combine
  (is (= (sut/move-up test-board-3)
         [[4 2 2 16]
          [0 4 16 8]
          [0 16 0 16]
          [0 0 0 0]])))

(deftest move-down-and-combine
  (is (= (sut/move-down test-board-3)
         [[0 0 0 0]
          [0 2 0 8]
          [0 4 2 16]
          [4 16 16 16]])))

(deftest move-test
  (testing "direction works!"
    (with-redefs [board/random-tile identity]
      (is (= (sut/move test-board-3 ::sut/left)
             (sut/move-left test-board-3)))

      (is (= (sut/move test-board-3 ::sut/right)
             (sut/move-right test-board-3)))

      (is (= (sut/move test-board-3 ::sut/up)
             (sut/move-up test-board-3)))

      (is (= (sut/move test-board-3 ::sut/down)
             (sut/move-down test-board-3)))))

  (testing "add random tile only when board has changed"
    (with-redefs [board/random-tile (fn [_] ::random-tile-added)]
      (let [board [[2 0 0 0]
                   [2 0 0 0]
                   [4 0 0 0]
                   [4 0 0 0]]]
        (is (= (sut/move board ::sut/left)
               board))

        (testing "random tile is added"
          (is (= (sut/move board ::sut/up)
                 ::random-tile-added)))))))
