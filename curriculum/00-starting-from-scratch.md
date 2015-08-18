Twinkle Little Star - Making Sounds with Overtone
=================================================

Staring from scratch
---------------------

If you have cloned out github repository,
https://github.com/ClojureBridge/tones,
it has a project already setup.
You can start by evaluating a file, `play.clj`.

However, starting from scratch, in another words, starting from
creating a project, would be a good training to get yourself
familiarize to a Clojure application. 

This document explains how to create a project that uses Overtone.

### 1. Create a project

Like we learned by the
[first project](https://github.com/ClojureBridge/drawing/blob/master/curriculum/first-program.md#create-a-project),
create a project using `lein new`.
We may put the first project and this together and make a single
project; however, to make it simple and easy to understand, let's create a
brand new project.
Once you become familiar with Clojure's project and its setting,
it would be a good training to put these projects together.

To create a new project, open the terminal and run this command:

```bash
lein new explore-overtone
```

Where you should see something like:

    Generating a project called explore-overtone based on the 'default' template.
    The default template is intended for library projects, not applications.
    To see other templates (app, plugin, etc), try `lein help new`.

This is just a friendly message, and you don't worry about this.

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

Be aware that we got the directory, `explore_overtone`, which is not
`explore-overtone`. (see `-` and `_`)
This is to manage Java languages's restriction. Clojure is written on
top of Java, so sometimes, Clojure needs to care about such restrictions.


### 2. Add Overtone to the project

Once the project has been created, we need to edit the
`project.clj` to add Overtone as a required library (dependency).

Open `project.clj` and change the file from this:

```clojure
(defproject explore-overtone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]])
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

The differences are two:

1. `[org.clojure/clojure "1.7.0"]`

    We use newer version of Clojure.

2. `[overtone "0.9.1"]`

    We just added the Overtone library to our project


> Newer versions of libraries are continually released.
> To check what version is the latest,  go to https://clojars.org/ and
> type library name, for example, overtone.


### 3. Test the project

If you finish editing `project,clj`, start `lein repl`.

```bash
lein repl
```

The first time you run this, the `lein` program will take a while as
it downloads our Overtone world.

While all the libraries have been downloaded, they aren’t loaded into
our REPL&#x2026;not just yet. Bring them into our session, by typing the
following into the REPL:

```clojure
(use 'overtone.live)
```

This command may spit out some technical warnings, but we’ll ignore
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

Like we loaded Overtone library on `lein repl` by typing `use`,
the library should be loaded from Clojure file also.
To do this, we need to add the line below to `ns` form.

```clojure
  (:use [overtone.live])
```

Additionally, we want to add one more library since we are going to
use piano notes. So, our `use` form will be:

```clojure
  (:use [overtone.live]
        [overtone.inst.piano]))
```

Since Overtone is designed to be quite modular, just loading
`overtone.live` isn't enough to make piano sounds.
You can make your computer beep machine, but not piano player.
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
Let's change the `foo` function to make sound.

Change `foo` function to this:

```clojure
(defn foo
  "I make a piano note"
  []
  (piano))
```

#### run `foo` function

To run `foo` function, evaluate `core.clj` file.
Then, type `(foo)`, and evaluate this line.
Did you hear the sound from your computer?
