(ns day1
  (:require [clojure.string :as string]))

(defn volume->fuel
  "Calculate fuel consumption for a given volume."
  [volume]
  (-> (/ volume 3) Math/floor (- 2)))


(comment

  ;; Fuel requirement for task 1
  (->> "input/day1.txt"
       slurp
       string/split-lines
       (transduce (comp (map read-string)
                        (map volume->fuel))
                  +
                  0))


  ;; Fule requirement for task 2
  (->> "input/day1.txt"
       slurp
       string/split-lines
       (transduce (comp (map read-string)
                        (map #(->> (iterate volume->fuel %)
                                   (drop 1)
                                   (take-while pos?)))
                        (map #(reduce + %)))
                  +
                  0))
  )


