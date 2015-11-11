Twinkle Twinkle Little Star - Making Sounds with Overtone
=================================================

This is the story of Meg, who recently attended a ClojureBridge
workshop.  Meg has always been a bit of a music nerd, and she wondered why
DJs often used laptops on stage. Then she saw a live music coding
video, and read that they use Clojure to create the music. Intrigued,
she started to investigate Overtone.

[Overtone](http://overtone.github.io) provides a way to generate sounds,
synthetic instruments, and music using Clojure. While it is very advanced, it is
pretty easy to get started, and interesting to explore.

**Linux Users**
If you are on Linux, you need to have the jack daemon running. Take a
look at
[Installing and starting jack](http://github.com/overtone/overtone/wiki/Installing-and-starting-jack),
and install the packages.


1. Prelude - getting started
----------------------------

### Download the project

Meg learned that ClojureBridge already has a template project for
Overtone. The first thing to do to get started is to *clone* that project
using `git` command.

Meg opened the terminal and typed the command:

```bash
git clone https://github.com/ClojureBridge/tones.git
```

> (Option) It's a good exercise to start from creating a Clojure
> project. [Starting from scratch](00-starting-from-scratch.md)
> explains how to do that. Try it later.

### Evaluate the file

Soon, the `git` command downloaded the project, so Meg started Light Table
and opened the file `tones/src/tones/play.clj`
Next, she evaluated `play.clj` by hitting
<kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>Enter</kbd>
(or <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Enter</kbd>),
which took a while to finish.

When it completed, hey listen, music!
The familiar melody of "Twinkle Twinkle Little Star" came out from
Meg's computer.

> If you didn't hear anything, check the volume.
> You did turn up the volume, right?
>
> **Linux and Windows Users** Probably, you need to startup an
> external server and connect to it. Please see
> https://github.com/ClojureBridge/tones/blob/master/curriculum/01-piano-chords.md
> for details.

Meg moved the cursor right next to `(twinkle)` and evaluated this line
by hitting
<kbd>Cmd</kbd> + <kbd>Enter</kbd>
(or <kbd>Ctrl</kbd> + <kbd>Enter</kbd>).
Again, she heard the music. It was the first part of "Twinkle Twinkle Little
Star" played by a piano.

> To do this in a repl, cd to the tones project root directory and
> run `lein repl`. Once the repl starts up, `(require 'tones.play)`,
> which loads and evaluates the file.  You should hear a melody once
> the file evaluation finishes.  To listen the melody again, change
> the namespace by typing `(ns tones.play)`, then `(twinkle)`.

### What to look at

Although the music was a children's song, it was enough to make Meg
very excited.
Immediately, Meg went to the Overtone github repository in a
browser, https://github.com/overtone/overtone, and looked around the
repository.
She found many examples there. Among those, these two looked helpful
to make piano sounds.

* https://github.com/overtone/overtone/blob/master/src/overtone/examples/getting_started/pragpub-article.clj
* https://github.com/overtone/overtone/blob/master/src/overtone/examples/workshops/resonate2013/ex01_phrasestudy.clj


2. Etude - playing piano notes
------------------------------

#### Basic `piano` function usage

Meg started using Overtone functions.
The first function she tried was `piano` with no arguments.
Meg typed the function below in the file, `play.clj`:

```clojure
(piano)
```

She then evaluated this line and, yes!, she heard a piano note from her
computer. Meg evaluated this simple function several times with joy.

### The `piano` function argument

Meg has already learned that the `piano` function takes a note number as an
argument. For example:

```clojure
(piano 48)
```

The number `48` corresponds to a specific note, but it didn't help Meg
to understand what note she made. She knew that musicians are used to
notes, not numbers, so they would want to write notes instead of
numbers as the argument.  Meg found a nice Overtone converter
function, `note`.

When Meg used the function like this:

```clojure
(note :c3)
```

it returned the number `48`, so the value of `:c3` is `48`.

Instead of just randomly trying various notes, Meg googled to find the
mapping between numbers and notes and found this chart:

![midi note](img/midi-int-midi-note-no-chart.jpg)

And this little nugget from [Understanding musical theory](http://wiki.spheredev.org/Understanding_musical_theory):

![notes](img/Theory-staff-cmajortreble.png)

Looking at this information, Meg understood that `:c3` expressed a `C` note in the
third octave. Now, she could make a piano note like this:

```clojure
(piano (note :c3))
```

> When you don't give the `piano` function a number argument, it uses
> `60` by default, which corresponds to `:c4`, otherwise known as
> "middle C".


### The `chord` function

While googling, Meg noticed that a *chord* is the sound of two or more
notes played at the same time, like this:

![chord](img/chords_c3.png)

Meg realized that Overtone's `chord` function was a handy chord
generator, which returns the numbers that correspond to the notes of the chord.
Too see how to use `chord` function, Meg looked at the document.
She moved the cursor right next to the function name *chord*,
right clicked and selected "show docs".

> In the repl, `(doc chord)`


```clojure
overtone.live
([root chord-name] [root chord-name inversion])
Returns a set of notes for the specified chord. The root must be in
  midi note format i.e. :c4.

  (chord :c4 :major)  ; c major           -> #{60 64 67}
  (chord :a4 :minor)  ; a minor           -> #{57 60 64}
  (chord :Bb4 :dim)   ; b flat diminished -> #{70 73 76}
```

Meg said to herself, "ah, that's why a function call"

```clojure
(chord :c3 :major)
```

"returned"
```clojure
(48 55 52)
```

"these three, got it."

She completely understood the `chord` function, its meaning and how to use.
Now she wanted to make the piano sound of
`(chord :c3 :major)`, so
she typed `(piano 48) (piano 55) (piano 52)` in the same line and
evaluated the line, but the sound was just a single note.

> On repl, writing `(piano 48) (piano 55) (piano 52)` in a single line
> works and makes a chord sound. But unfortunately, this doesn't work
> on Light Table.

Meg thought a while, recalling what she had learned at the ClojureBridge
workshop. Suddenly, the idea came to her mind, "write a function!"
Soon, she wrote a function, `c3-major-chord` in `play.clj` file:

```clojure
;; function definition
(defn c3-major-chord
  []
  (piano 48)
  (piano 55)
  (piano 52))
```

At the last line of the function, she evaluated it.
Then, wrote another line of code to use this function.

```clojure
;; usage
(c3-major-chord)
```

On the right end of this line, she evaluated it also.
Hey! A piano chord! Meg heard the sound of a chord, not just a single note.


### A sequence of notes

Meg looked at her function with satisfaction for a while.
At first, it looked nice, but a repetition of the same function made
her think, "is there any better way to do this?"

She went to ClojureBridge curriculum site on the browser,
http://clojurebridge.github.io/curriculum/#/ ,
then realized,
"Icky. This is Clojure, which is very good at
*iterating* over a *sequence*."
In this case, the `doseq` function fits well, which she learned from the
ClojureBridge main curriculum.

Since the `chord` function returns a sequence of numbers,
the repetition should be replaced by:

```clojure
(doseq [note (chord :c3 :major)]  ;; Sequence
  (piano note))                   ;; Behavior
```

The `doseq` binds the value of the first note to the `note`
symbol, and then calls `(piano note)` to play it. Then it binds
the value of the second note to `note` and calls `(piano note)` again.
And then, one more time, with the third note.

When Meg evaluated this `doseq`, she could hear the same chord
as three piano functions.


### Writing a function that takes arguments

At first, Meg rewrote the `c3-major-code` function just replacing
the repetition by the `doseq`:

```clojure
;; function definition
(defn c3-major-chord
  []
  (doseq [note (chord :c3 :major)]
    (piano note)))
```

Again, looking at the function, she wondered how to make this function
more general. That's because this function can make only c3 major
chord, but a bunch of other chords are out there, and a bunch of
functions, one for
each chord, didn't make sense.
"Aha! I should change the function so that it will take arguments," she shouted and smiled.

Since the `chord` function takes two arguments, root and chord-name,
this new function needs two arguments, also.
She changed it to take two arguments, and gave it a more general name, `piano-chord`:

```clojure
;; function definition
(defn piano-chord [root chord-name]
  (doseq [note (chord root chord-name)]
    (piano note)))
```

Next, she wrote lines of code to use it.

```clojure
;; usage
(piano-chord :c3 :minor)
(piano-chord :f3 :minor)
(piano-chord :g2 :major)
(piano-chord :c3 :minor)
```

Her feeling was quite happy when she evaluated the function and these lines one by one.

### [BONUS] change the function to take multiple sets of arguments

Meg noticed that the *chord* function can take an optional third
argument representing the `inversion` of the chord, and she wanted her
`piano-chord` function to have the same flexiblity.

> An inversion is a re-ordering of the notes in a chord. For example,
> the :c3 :major chord has the notes :c3, :e3, and :g3, which
> correspond to the numbers 48, 52, and 55.  To make the "first
> inversion" of the chord, you move the :c3 up an octave to :c4, so
> the notes are now :e3, :g3, and :c4, which correspond to the numbers
> 52, 55, and 60.

Clojure has a way to define a function that can take different numbers
of arguments, or *arities*.
Using this feature, the Meg's `piano-chord` function became like this:

```clojure
(defn piano-chord
  ([root chord-name]
    (doseq [note (chord root chord-name)]
      (piano note)))
  ([root chord-name inv]
    (doseq [note (chord root chord-name inv)]
      (piano note))))
```

The `piano-chord` function definition got two body entries,
where the first element of each is a vector of the
parameters. Based on the number of arguments, 2 or 3, either the
first or second body is executed.

If this gets evaluated:

```clojure
(piano-chord :c3 :minor)
```

the first body entry will be used. When the arguments are three:

```clojure
(piano-chord :c2 :dim 1)
```

the second body is called. This is a *seriously* moody chord.
There are a lot of different kinds of chords, and Overtone supports
many of them. Here are a few more examples - try "playing" them all in
a row.

```clojure
(piano-chord :g3 :7sus4)
(piano-chord :g3 :dom7)
(piano-chord :c4 :sus4)
(piano-chord :c4 :minor)
```

### Make a melody

So far, Meg enjoyed making piano notes and chords.
It was absolutely fun, but she wondered how to make a melody.
When the `twinkle` function in `play.clj` file got evaluated, it
played the melody of Twinkle Twinkle Little Star, and she wanted to do something
like that. Among Overtone documents and examples, she found that
the answer was to introduce a *progression* with the `at` function.

The idea of a *progression* is that, by setting time differences to successive
notes or chords, for example, note1 at now, note2 at 1 second later,
note3 at 2 seconds later, and so on, it shifts the time to play each
sound. This is why the `at` function takes time for its first argument.

Meg used `at` function and wrote this:

```clojure
(let [time (now)]
  (at         time  (piano-chord :d4 :minor7))
  (at (+ 2000 time) (piano-chord :g3 :major7))
  (at (+ 4000 time) (piano-chord :c3 :major7))
  (at (+ 6000 time) (piano-chord :e3 :minor7)))
```

She used a `let` binding, which she learned from the Functions module
in the ClojureBridge main curriculum. With a `let` binding, time holds
the current time (the time you hit the ctrl/cmd + space), which is the
return value of the `now` function.  The `now` function returns the value
of the current time in *milliseconds* (1 second = 1000 milliseconds).

The code above works like this:

1. play piano chord of :d4 :minor7 now
2. play piano chord of :g3 :major7 2 seconds later from now
3. play piano chord of :c3 :major7 4 seconds later from now
4. play piano chord of :e3 :minor7 6 seconds later from now

In another words, four piano chords are *scheduled* to play every 2
seconds.

Meg changed the parameters a bit like this:

```clojure
(let [time (now)]
  (at time (piano-chord :c4 :minor))
  (at (+ 1500 time) (piano-chord :f3 :minor 2))
  (at (+ 3000 time) (piano-chord :g3 :major 1))
  (at (+ 4100 time) (piano (note :f4)))
  (at (+ 4500 time) (piano-chord :c3 :minor 2)))
```

Hey, this sounds like real music!


### Complete Twinkle Twinkle Little Star

Meg remembered that the `twinkle` function played only the first part
of Twinkle Twinkle Little Star, and she wanted to add the next
part. That would be a nice exercise.  She googled and found the score
of this well-known lullaby.

![Twinkle Twinkle Little Star](img/TwinkleTwinkle_C_Image.jpg)

"OK, so I already have the melody from the beginning to 'what you
are'. All I need to add is 'Up above the world so high', repeat it,
and then repeat the first part," Meg murmered.

Reading the score, she figured out that she needed code to play the
notes `:g3 :g3 :f3 :f3 :e3 :e3 :d3`, and then she could combine
functions that she had already learned to play the melody. After a bit
of experimentation and testing, she wrote the up-above `function`:

```clojure
;; function definition
(defn up-above
  [start]
  (let [step 650
        notes [:g3 :g3 :f3 :f3 :e3 :e3 :d3]]
    (dotimes [i (count notes)]
      (at (+ start (* i step)) (piano (note (nth notes i)))))))
```

She gave the `up-above` function a `start` parameter so she could
delay the start until after the first part of the song.  She chose the
[`dotimes`](http://clojuredocs.org/clojure.core/dotimes) function to
execute the `at` function multiple times.  `nth` is the function she
learned in the Data Structures module in the ClojureBridge main
curriculum. She also tried some variations of `step` variations to
find a good interval between notes.

Meg tested the function she just wrote:

```clojure
(up-above (now))
```

It sounded good. The last piece was to play all, `twinkle`,
`up-above`, `up-above`, then `twinkle` in order.

```clojure
(let [start (now)]
  (twinkle start)
  (up-above (+ start 10400))
  (up-above (+ start 15600))
  (twinkle (+ start 20800)))
```

When Meg evaluated this `let` form at the end of line,
she heard the whole melody of Twinkle Twinkle Little Star played
on a piano!


4. Finale - epilogue
---------------------

Whew. This lesson covered a lot of ground including a bit about Overtone's
interface to SuperCollider music synthesis engine and how to use it.

It's really hard to imagine creating a song like this way, in another
words, this amount of typing. Probably, the work would require a
serious editor to keep our fingers from getting bloody.
Clojure should be all about *succinctness*, however, let’s leave this
for now and look at other useful ways to make music.

If you are interested in playing realistic sounds, look at
[this essay and code](http://blog.josephwilk.net/clojure/creating-instruments-with-overtone.html)
for building up an ethereal-sounding flute solo.
If you want to know making music without typing so much
like this lesson, check out the
[Leipzig Library](https://github.com/ctford/leipzig),
which explains how to build this sort of music compositions easier.

Have fun with making sounds!
