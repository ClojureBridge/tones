(ns tones.core
  (:use [overtone.live]
        [overtone.inst.piano]))

;; twinkle twinkle little star
(def phrase1 [:c3 :c3 :g3 :g3 :a3 :a3 :g3])

;; how I wonder what you are
(def phrase2 ['(:f3 :a3) '(:f3 :a3) '(:e3 :g3) '(:e3 :g3) '(:b2 :d3 ) '(:b2 :d3 ) '(:c3 :e3 :g3)])

(defn play-note-or-notes [n-or-ns]
  (if (sequential? n-or-ns)
    (doseq [n n-or-ns] (piano (note n)))
    (play-note-or-notes (list n-or-ns))))

(defn play-phrase
  [start step phrase]
  (dorun
   (map-indexed
    (fn [i n-or-ns]
      (at (+ start (* i step)) (play-note-or-notes n-or-ns)))
    phrase)))

(defn twinkle
  "Plays a part of Twinkle Twinkle Little Star.
  If time is not given, starts playing immediately."
  ([] (twinkle (now)))
  ([time]
      (let [start1 time
            step1 680
            start2 (+ start1 (* step1 (inc (count phrase1))))
            step2 640]
        (play-phrase start1 step1 phrase1)
        (play-phrase start2 step2 phrase2))))
