(ns day1
  (:require [clojure.string :as string]))

(defn volume->fuel
  "Calculate fuel consumption for a given volume."
  [volume]
  (-> (/ volume 3)
      Math/floor
      (- 2)))

(defn fuel-volumes
  "Recursively calculate fuel until vector contains a negative value.
  Drop the negative volume (tail) and the module weight (head) to get
  vector of fuel consumption volumes."
  [volumes]
  (if (neg? (last volumes))
    (rest (butlast volumes))
    (recur (conj volumes
                 (volume->fuel (last volumes))))))

(comment

  ;; Fuel requirement for task 1
  (time (->> "input/day1.txt"
             slurp
             string/split-lines
             (transduce (comp (map read-string)
                              (map volume->fuel))
                        +
                        0)))


  ;; Fule requirement for task 2
  (time (->> "input/day1.txt"
             slurp
             string/split-lines
             (transduce (comp (map read-string)
                              (map vector)
                              (map fuel-volumes)
                              (map #(reduce + %)))
                        +
                        0)))
  )


