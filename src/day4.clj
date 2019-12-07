(ns day4)

(defn number->digits
  "https://stackoverflow.com/a/29942388/1950048"
  [n]
  (->> n
       (iterate #(quot % 10))
       (take-while pos?)
       (mapv #(mod % 10))
       rseq))

(defn double-digits?
  [number]
  (->> (str number)
       (re-find #"([0-9])\1")))

(defn double-digits2?
  [number]
  (->> (number->digits number)
       (partition-by identity)
       (filter #(= (count %) 2))
       not-empty))

(defn increasing-digits?
  [number]
  (->> (number->digits number)
       (partition 2 1)
       (filter #(< (second %) (first %)))
       empty?))

(comment

  ;; Task 1 using regex for double-digits
  (->> (range 356261 (inc 846303))
       (filter (every-pred double-digits?
                           increasing-digits?))
       count)

  ;; Task 2 turning numbers into digit sequence and using partition
  ;; to find groups of number, then counting the length of each partition.
  (->> (range 356261 (inc 846303))
       (filter (every-pred double-digits2?
                           increasing-digits?))
       count))

