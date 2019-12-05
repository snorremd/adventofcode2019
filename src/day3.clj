(ns day3
  (:require [clojure.edn :as edn]
            [clojure.string :as string]))


(defn abs
  "Get the absolute value of the number"
  [n]
  (max n (- n)))


(defn manhatten-dist
  [[x1 y1] [x2 y2]]
  (+ (abs (- x1 x2))
     (abs (- y1 y2))))


(defn map-intersection
  [map1 map2]
  (select-keys map1 (keys map2)))


(defn line-str->coords
  [line-str]
  (->> (string/split line-str #",")
       (map #(split-at 1 %))
       (map (fn [[direction length]]
              [(-> direction first str keyword)
               (edn/read-string (string/join length))]))
       (reduce (fn [coords [direction length]]
                 (let [[[x y] counter] (last coords)]
                   (into coords
                         (map #(case direction
                                 :U [[x (+ y %)] (+ counter %)]
                                 :D [[x (- y %)] (+ counter %)]
                                 :L [[(- x %) y] (+ counter %)]
                                 :R [[(+ x %) y] (+ counter %)])
                              (range 1 (inc length))))))
               [[[0 0] 0]])))


(comment
  ;; Task 1 calculates closest intersection by calculating all x y coordinates for each cable
  ;; making each a map (for convenience in next task), and then taking the intersection of those
  ;; maps. Then we calculate the manhatten distance for each x y coordinate in the intersection
  ;; finding the shortest.
  (->> (slurp "./input/day3.txt")
       string/split-lines
       (map line-str->coords)
       (map #(into {} %))
       (apply map-intersection)
       (#(dissoc % [0 0]))
       keys
       (sort-by (partial manhatten-dist [0 0]))
       first
       (manhatten-dist [0 0]))

  ;; Task 2 calculates all coordinates like before, which includes keeping count of
  ;; the distance traveled for each coordinate. We keep a reference to the two maps
  ;; of coordinates for each line and after finding the intersection calculate the combined
  ;; distance for each coordinate. Then we just sort the result.
  (let [[coords1 coords2] (->> (slurp "./input/day3.txt")
                               string/split-lines
                               (map line-str->coords)
                               (map #(into {} %))
                               (map #(dissoc % [0 0])))]
    (->> (map-intersection coords1 coords2)
         (map (fn [[xy dist]]
                (+ dist
                   (coords2 xy))))
         sort
         first)))


