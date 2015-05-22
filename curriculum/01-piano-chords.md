The following *after-bridge* extension will further expand your
knowledge of Clojure by investigating a Clojure extension for creating music.

> This is a story of Meg who attended a ClojureBridge workshop
> recently.  Meg has always been a bit of a music nerd, but wondered
> why DJs always used laptops on stage. Then she saw a live music
> coding video, and read that they use Clojure to create the
> music. Intrigued, she started to investigate Overtone.

[Overtone](http://overtone.github.io) is a way to generate sounds,
instruments and music using Clojure. While it is very advanced, it is
pretty easy to get started, and interesting to explore.

# Creating a Project with Overtone

Like most Clojure projects, we begin by using `lein` to create a
project to contain our programs:

    lein new explore-overtone

Where you should see something like:

    Generating a project called explore-overtone based on the 'default' template.
    The default template is intended for library projects, not applications.
    To see other templates (app, plugin, etc), try `lein help new`.

Once the skeleton program has been created, we need to edit the
`project.clj` to add Overtone as a required library (dependency), so
change the file from this:

    (defproject explore-overtone "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.6.0"]])

To this:

    (defproject explore-overtone "0.1.0-SNAPSHOT"
      :description "FIXME: write description"
      :url "http://example.com/FIXME"
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}
      :dependencies [[org.clojure/clojure "1.6.0"]
                     [overtone "0.9.1"]])

For the first part of our exploration of Overtone, we will use a
REPL:

    lein repl

The first time you run this, the `lein` program will take a while as
it downloads our Overtone world.

While all the libraries have been downloaded, they aren’t loaded into
our REPL&#x2026;not just yet. Bring them into our session, by typing the
following into the REPL:

    (use 'overtone.live)

This command may split out some technical warnings, but we’ll ignore
them for the moment, as we bask in welcome message:

    SC_AudioDriver: sample rate = 44100.000000, driver's block size = 512
    Exception in World_OpenUDP: unable to bind udp socket

    --> Connecting to internal SuperCollider server...
    --> Connection established

        _____                 __
       / __  /_  _____  _____/ /_____  ____  ___
      / / / / | / / _ \/ ___/ __/ __ \/ __ \/ _ \
     / /_/ /| |/ /  __/ /  / /_/ /_/ / / / /  __/
     \____/ |___/\___/_/   \__/\____/_/ /_/\___/

       Collaborative Programmable Music. v0.9.1


    Cometh the hour, cometh your.name.here, the overtone hacker.

    nil
    user=>

# Playing Piano Notes

Overtone is quite modular, and even at this point, not everything is
available to us. Let’s bring in a piano sound, by typing the
following to use a piano function:

    (use 'overtone.inst.piano)

And let’s play a note, by calling the `piano` function:

    (piano)

Did you hear a piano note? Great! If not, we may have to diagnose
our audio (you did turn up the volume, right?).<sup><a id="fnr.1" name="fnr.1" class="footref" href="#fn.1">1</a></sup> You can hit the up
arrow and the Return key in the REPL to quickly repeat this function.

The `piano` function takes a note number:

    (piano 48)

The `48` is the number that corresponds to the note. Since musicians
are used to notes, not numbers, Overtone includes a `note` function:

    (note :c3)

Executing this function returns `48`, so the value of `48`
corresponds to a `C` note in the third octave. We could play our
note this way:

    (piano (note :c3))

Let’s look at another function, `chord`. We can get more information
about it by using the `doc` function. Type this into the REPL:

    (doc chord)

Which returns:

    user=> (doc chord)
    -------------------------
    overtone.live/chord
    ([root chord-name] [root chord-name inversion])
      Returns a set of notes for the specified chord. The root must be in
      midi note format i.e. :C4.

      (chord :c4 :major)  ; c major           -> #{60 64 67}
      (chord :a4 :minor)  ; a minor           -> #{57 60 64}
      (chord :Bb4 :dim)   ; b flat diminished -> #{70 73 76}

Ah, so the following function call:

    (chord :c3 :major)

Returns:

    (48 55 52)

This returns the three note numbers that correspond to the C major
chord. While we **could** type all this one the same line to play a
chord:

    (piano 48) (piano 55) (piano 52)

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
