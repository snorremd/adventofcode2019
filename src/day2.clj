(ns day2
  (:require [clojure.string :as string]))


(defn int-machine
  [instr-ptr memory]
  (let [[opcode ptr1 ptr2 ptr3] (subvec memory instr-ptr)
        op                      (case opcode 1 + 2 * identity)]
    (if (= opcode 99)
      memory
      (recur (+ instr-ptr 4)
             (assoc memory
                    ptr3
                    (op (memory ptr1)
                        (memory ptr2)))))))


(comment

  ;; Task 1
  (as-> (slurp "./input/day2.txt") x
    (string/split x #",")
    (mapv read-string x)
    (assoc x 1 12 2 2)
    (int-machine 0 x)
    (first x))

  ;; Task 2
  (as-> (slurp "./input/day2.txt") res
    (string/split res #",")
    (mapv read-string res)
    (for [x (range 100) y (range 100)] [res x y])
    (pmap (fn [[memory x y]]
            [x y (-> (assoc memory 1 x 2 y)
                     (#(int-machine 0 %))
                     first)])
          res)
    (drop-while #(not= (get % 2) 19690720) res)
    (first res)
    (+ (* 100 (first res))
       (second res))))
