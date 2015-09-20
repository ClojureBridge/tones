(ns tones.twinkle
  (:use [overtone.live]
        [overtone.inst.piano]
        [tones.core]))

;; twinkle twinkle little star
(def phrase1 [:c3 :c3 :g3 :g3 :a3 :a3 :g3])

;; how I wonder what you are
(def phrase2 ['(:f3 :a3) '(:f3 :a3) '(:e3 :g3) '(:e3 :g3) '(:b2 :d3 ) '(:b2 :d3 ) '(:c3 :e3 :g3)])

(defn twinkle
  "Plays a part of Twinkle Twinkle Little Star."
  []
  (let [step 650
        start1 (now)
        start2 (+ start1 (* step (inc (count phrase1))))]
    (play-phrase start1 step phrase1)
    (play-phrase start2 step phrase2)))
