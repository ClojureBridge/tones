(ns tones.core
  (:use [overtone.live]
        [overtone.inst.piano]))

;; twinkle twinkle little star
(def phrases1 [:c3 :c3 :g3 :g3 :a3 :a3 :g3])

;; how I wonder what you are
(def phrases2 ['(:f3 :a3) '(:f3 :a3) '(:e3 :g3) '(:e3 :g3) '(:b2 :d3 ) '(:b2 :d3 ) '(:c3 :e3 :g3)])

(defn melody1
  [start step phrases]
  (let [time start
        numbers (map note phrases)]
    (dorun (map-indexed (fn [i n] (at (+ time (* i step)) (piano n))) numbers))))

(defn melody2
  [start step phrases]
  (let [time start
        chords (fn [p] (doseq [n p] (piano (note n))))]
    (dotimes [i (count phrases)]
      (at (+ (* i step) time) (chords (nth phrases i))))))

(defn twinkle
  "plays a part of Twinkle Little Star.
  if time is not given, starts playing immediately."
  ([] (twinkle (now)))
  ([time]
      (let [start1 time
            step1 680
            start2 (+ start1 (* step1 (+ 1 (count phrases1))))
            step2 640]
        (melody1 start1 step1 phrases1)
        (melody2 start2 step2 phrases2))))

