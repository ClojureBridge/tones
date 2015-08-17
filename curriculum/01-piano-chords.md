Twinkle Stars - Making Sounds with Overtone
===========================================

This is a story of Meg who attended a ClojureBridge workshop
recently.  Meg has always been a bit of a music nerd, but wondered
why DJs always used laptops on stage. Then she saw a live music
coding video, and read that they use Clojure to create the
music. Intrigued, she started to investigate Overtone.

[Overtone](http://overtone.github.io) is a way to generate sounds,
instruments and music using Clojure. While it is very advanced, it is
pretty easy to get started, and interesting to explore.


1. Prelude - getting started
----------------------------

### Download the project

Meg has already learned ClojureBridge has a template project for
Overtone. The first thing to get started is to *clone out* that
project using `git` command.

Meg opened the terminal and typed the command:

```bash
git clone https://github.com/ClojureBridge/tones.git
```

> (Option) It's a good practice to start from creating a Clojure
> project. [Starting from scratch](00-starting-from-scratch.md)
> explains how to do that. Try it later.

### Evaluate the file

Soon, `git` command downloaded the project, so Meg started Light Table
and opened the file `tones/src/tones/play.clj`
Next, she evaluated `play.clj` by hitting
<kbd>Cmd</kbd> + <kbd>Shift</kbd> + <kbd>Enter</kbd>
(or <kbd>Ctrl</kbd> + <kbd>Shift</kbd> + <kbd>Enter</kbd>),
which took a while to finish.

When it completed, hey listen, music!
Familiar melody of "Twinkle Little Star" came out from Meg's computer.

Meg moved the cursor right next to `(twinkle)` and evaluated this line
by hitting
<kbd>Cmd</kbd> + <kbd>Enter</kbd>
(or <kbd>Ctrl</kbd> + <kbd>Enter</kbd>).
Again, she heard the music. It was the first part of "Twinkle Little
Star" played by a piano.

> On `lein repl`, start up repl at the top tones directory.
> Next, `(require 'tones.play)`, which loads and evaluates the file.
> You should hear a melody once the file evaluation finishes.
> To listen the melody again, change the namespace by typing
> `(ns tones.play)`, then `(twinkle)`.

### What to look at

Although the music was childish, naive one, it was enough to make Meg
very excited.
Immediately, Meg went to the Overtone github repository on the
browser, https://github.com/overtone/overtone, and looked around the
repository.
She found many examples there. Among those, these two looked helpful
to make piano sounds.

* https://github.com/overtone/overtone/blob/master/src/overtone/examples/getting_started/pragpub-article.clj
* https://github.com/overtone/overtone/blob/master/src/overtone/examples/workshops/resonate2013/ex01_phrasestudy.clj


2. Etude - Playing Piano Notes
------------------------------

#### basic `piano` function usage

Meg started using Overtone functions.
The first function she tried was `piano` without any argument, very
basic one.
Meg typed the function below in the file, `play.clj`:

```clojure
(piano)
```

then, evaluated this line. Yes! She heard a piano note from her
computer. Meg evaluate this simple function a couple of times with joy.

> If you didn't heard anything, check the volume.
> you did turn up the volume, right?

### `piano` function argument

Meg has already learned `piano` function takes a note number as an
argument. For example:

```clojure
(piano 48)
```

The `48` is the number that corresponds to the note.
However, just numbers were bit hard to figure out what note she made.
Actually she has heard musicians are used to notes, not numbers,
so they want to write notes instead of number as the argument.
Meg found a nice Overtone converter function, `note`.

When Meg used the function like this:

```clojure
(note :c3)
```

it returned `48`, so the value of `:c3` is `48`.

Before randomly try various notes, Meg googled to find the
correspondence between numbers and notes. Soon, she found many.
For example,

![midi note](img/midi-int-midi-note-no-chart.jpg)

![notes](img/Theory-staff-cmajortreble.png)
from [Understanding musical theory](http://wiki.spheredev.org/Understanding_musical_theory)

Looking at the chart, Meg understood `:c3` expressed a `C` note in the third octave starting
from zero octave. Now, she could make a piano note like this:

```clojure
(piano (note :c3))
```


### `chord` function

While googling, Meg noticed that there were the sound called
a *chord*, a mixture of a couple of notes, like this:

![chord](img/chords_c3.png)

Meg realized that Overtone's `chord` function was a handy chords
generator, which returns three numbers of the chord.
Too see how to use `chord` function, Meg looked at the document.
She moved the cursor right next to the function name *chord*,
right clicked and selected "show docs".

> On repl, `(doc chord)`


```clojure
overtone.live
([root chord-name] [root chord-name inversion])
Returns a set of notes for the specified chord. The root must be in
  midi note format i.e. :C4.

  (chord :c4 :major)  ; c major           -> #{60 64 67}
  (chord :a4 :minor)  ; a minor           -> #{57 60 64}
  (chord :Bb4 :dim)   ; b flat diminished -> #{70 73 76}
```

Meg talked to herself, "ah, that's why a function call this":

```clojure
(chord :c3 :major)
```

"returned":
```clojure
(48 55 52)
```

"Got it."

She could understood the `chord` function, its meaning and how to use.
However, the problem was how to make the piano sound of
`(chord :c3 :major)`.
She typed `(piano 48) (piano 55) (piano 52)` in the same line and
evaluated the line, but the sounds was a single note.

> On repl, writing `(piano 48) (piano 55) (piano 52)` in a single line
> works and makes a chord sound. But unfortunately, this doesn't work
> on Light Table.

Meg thought a while, thinking what she learned at ClojureBridge
workshop. Suddenly, the idea came up on her mind, "write a function!"
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
Then, wrote another line to use this function.

```clojure
;; usage
(c3-major-chord)
```

On the right end of this line, she evaluated it also.
Hey! a piano chord! Meg heard the sound of a chord not a single note.


### sequence of notes

Icky. We are dealing with Clojure, and it has the ability to
*iterate* over a list of values (called a *sequence*), and run the
same function on it. The `doseq` (for *do a sequence*) is similar to
the `map` function you learned about during the main workshop in
that it takes two parameters:

 * The sequence of things, for instance, a list of note numbers.
 * A function repeatedly called with each element:

    (doseq [note (chord :c3 :major)]  ;; Sequence
           (piano note))              ;; Behavior

Notice that the first argument to `doseq` specifies the sequence as
an odd-looking vector: `[variable value ...]`

The number of elements in this vector need to be even, since the
first element will be the *variable* and the next one will be
evaluated for its *value*. In our case, the *value* is a call to
the `chord` function to give us a list of notes to play.

The `doseq` assigns the `note` variable the value of the first note,
and then calls `(piano note)` to play it. It then assigns the second
value from our `chord` function to `note`, and calls `(piano note)`
again&#x2026; rinse, and repeat.

Ha, ha! Should we make that simpler to type by creating a function?

    (defn piano-chord [root chord-name]
        (doseq [note (chord root chord-name)]
               (piano note)))

And now we can type the following if we aren’t feeling quite so happy
for major chords:

    (piano-chord :c3 :minor)

However, the `inversion` parameter for the original `chord` function
is optional, and our `piano-chord` should let us have this sort of
*alternate* behavior. When defining our `piano-chord` function, we
can specify each *behavior* based on how many parameters are given
to the function, like this:

    (defn piano-chord
      ([root chord-name]      (doseq [note (chord root chord-name)]
                                     (piano note)))
      ([root chord-name inv]  (doseq [note (chord root chord-name inv)]
                                     (piano note))))

Let’s parse this code. This function definition has two body
entries, where the first element of each is a vector of the
parameters. Based on the number of parameters, 2 or 3, either the
first or second line is executed.

In other words, if we call this function with two parameters (the
`root` note, and the `chord-name`, the first line is called. If we
specify three, the `root`, the `chord-name` *and* the `inversion`,
the second is called.

Let’s try playing a moody chord:

    (piano-chord :c3 :minor)

Or a *seriously* moody chord:

    (piano-chord :c1 :dim 4)

Or a popular chord that if you play it, you will want to resolve and
play something else&#x2026;

    (piano-chord :g3 :dom7)

Instead of typing in piano chords, let’s make a *progression*, and
have the computer play them in succession. This requires the `at`
function, which takes the time to play something and something to
play.

First, we create a variable, `time` to hold the current time by
calling the `now` function&#x2026;

    (let [time (now)]
      (at         time  (piano-chord :d4 :minor7))
      (at (+ 2000 time) (piano-chord :g3 :major7))
      (at (+ 4000 time) (piano-chord :c3 :major7))
      (at (+ 6000 time) (piano-chord :e3 :minor7)))

All but the first `at` call, tries to figure out some exact time
(from now) to play, by adding a number of *milliseconds* to `time`.
Keep in mind that all four `piano-chord` lines are evaluated, and
*scheduled* to play at some time. Overtone takes care of when.

Try again?

    (let [time (now)]
      (at         time  (piano-chord :c4 :minor))
      (at (+ 3000 time) (piano-chord :g3 :dom7))
      (at (+ 4000 time) (piano-chord :f3 :minor))
      (at (+ 7000 time) (piano-chord :g3 :dom7))
      (at (+ 8000 time) (piano-chord :c4 :minor)))

I can’t really imagine creating a song this way&#x2026;this amount of
typing would require a serious editor to keep our fingers from
getting bloody.
Clojure is all about *succinctness*, however, let’s leave this and
move on to other ways to make music.

4. Encore
---------


While we will move on, if you are interested in playing realistic
sounds, look at [this essay and code](http://blog.josephwilk.net/clojure/creating-instruments-with-overtone.html) for building up an
ethereal-sounding flute solo, or check out the [Leipzig Library](https://github.com/ctford/leipzig) for
making it easier to build this sort of music compositions without
all the typing we did in this last example.

# Summary

Whew. We covered a lot of ground in this lesson. We learned a bit
about the Overtone interface to the SuperCollider music synthesis
engine, and how to add that to our `project.clj` file.

We then learned how to include this library in a REPL, and played
around with some of the functions available to us to create some
piano chords.
