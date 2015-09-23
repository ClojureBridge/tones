(ns tones.core
  (:use [overtone.live]
        [overtone.inst.piano]))

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
