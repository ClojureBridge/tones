Twinkle Twinkle Little Star - Making Sounds with Overtone
=================================================

Staring from scratch
---------------------

The https://github.com/ClojureBridge/tones has a project set up
already.  If you have cloned the github repository, you can start by
evaluating the `play.clj` file.

Starting from scratch by creating a new project, however, is good
training to get yourself more familiar with Clojure applications. This
document explains how to create a project that uses
[Overtone](https://github.com/overtone/overtone).

### 1. Create a project

As we learned from the
[first project](https://github.com/ClojureBridge/drawing/blob/master/curriculum/first-program.md#create-a-project),
we can create a project using `lein new`. Open the terminal and run this command:

```bash
lein new explore-overtone
```

You should see something like:

    Generating a project called explore-overtone based on the 'default' template.
    The default template is intended for library projects, not applications.
    To see other templates (app, plugin, etc), try `lein help new`.

If the command runs successfully, it should create a directory structure like this:

```
explore-overtone/
  ├── LICENSE
  ├── README.md
  ├── doc
  │     └── intro.md
  ├── project.clj
  ├── resources
  ├── src
  │     └── explore_overtone
  │           └── core.clj
  └── test
        └── explore_overtone
              └── core_test.clj

6 directories, 6 files
```

Be aware that we got the directory `explore_overtone`, which is
different from `explore-overtone` (`_` vs `-`). This has to do with
the Java languages's filename restrictions. Clojure is written
on top of Java, so sometimes, Clojure needs to care about such
restrictions.


### 2. Add Overtone to the project

Once the project has been created, we need to edit the
`project.clj` file to add Overtone as a dependency (required library).

Open `project.clj` and change the file from this:

```clojure
(defproject explore-overtone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]])
```

To this:

```clojure
(defproject explore-overtone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [overtone "0.9.1"]])
```

The difference is:

* `[overtone "0.9.1"]`

    We just added the Overtone library to our project

> Newer versions of libraries are continually released.
> To check what version is the latest,  go to https://clojars.org/ and
> type library name, for example, overtone.


### 3. Test the project

When you finish editing `project,clj`, start `lein repl`.

```bash
lein repl
```

The first time you run this, the `lein` program will take a while as
it downloads our Overtone world.

Although the libraries have been downloaded, they aren’t loaded into
our REPL&#x2026;not just yet. Bring them into our repl session by typing the
following into the REPL:

```clojure
(use 'overtone.live)
```

This command may spit out some technical warnings, but we’ll ignore
them for the moment, as we bask in the welcome message:

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

#### Linux users need Jack

If you are on Linux, you need to have jack daemon running. Take a
look at the document
[Installing and starting jack](http://github.com/overtone/overtone/wiki/Installing-and-starting-jack),
and install packages.


### 4. Edit a Clojure file

When you ran `lein new`, the command created a template Clojure file
also. You may create another file, but let's use the template file for
now.


Open `src/explore_overtone/core.clj`, which has a foo function.

```clojure
;; src/explore_overtone/core.clj

(ns explore-overtone.core)

(defn foo
"I don't do a whole lot."
[x]
  (println x "Hello, World!"))
```

#### add overtone library

Just as we loaded the Overtone library in the repl by typing `use`,
the library must be loaded in the Clojure namespace also.
To do this, we need to add the line below to `ns` form.

```clojure
  (:use [overtone.live])
```

Additionally, we want to add one more library since we are going to
use piano notes, so our `use` form will be:

```clojure
  (:use [overtone.live]
        [overtone.inst.piano]))
```

Since Overtone is designed to be quite modular, just loading
`overtone.live` isn't enough to make piano sounds.
You can make your computer a beep machine, but not a piano player.
This is the reason we added `overtone.inst.piano`.

At this moment, our `core.clj` looks like:

```clojure.clj
;; core.clj with overtone libraries
(ns explore-overtone.core
  (:use [overtone.live]
        [overtone.inst.piano]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
```

#### function that plays a piano note

Now we are ready to make a piano note.
Let's change the `foo` function to make a sound:

```clojure
(defn foo
  "I make a piano note"
  []
  (piano))
```

#### run `foo` function

To run the `foo` function, evaluate the `core.clj` file and
then type `(foo)` and evaluate this line.
Did you hear the sound from your computer?
