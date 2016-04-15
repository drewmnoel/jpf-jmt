# Java PathFinder Memory Tools (`jpf-jmt`)

## Overview
The goal of this extension, `jpf-jmt`, is to JPF is to provide memory limiting and more accurate reporting than the built-in `ConsolePublisher` report. The `ConsolePublisher` report indicates the maximum memory used by the application, however this is the maximum memory allowed by the JVM, not the actual maximum usage.

## Features
The `jpf-jmt` extension provides the following main features:

1. A state space graph listener which provides information about the memory usage of the application
2. A search constraint which ensures the application does not exceed a certain limit of memory usage.

### Listener
The state space listener is based on the `StateSpaceDot` listener, with the following modifications:
* The label of each state is the current heap usage as of that moment in the application
* The label granularity may be set to one of: `B, KB, MB`

### Constraint
The search property (a constraint), ensures that that the memory usage of the system under test does not exceed some limit. This limit may be set to any non-zero positive value. The limit may be set in the form of bytes, kilobytes, megabytes, gigabytes, or percentage. The percentage is given as a percent (e.g. `150` rather than `1.5`), and calculates the limit as a numeric value at startup. That is, if the heap usage at start is `10 MB` and the limit is set to `200`, the search property will be violated if the memory exceeds `20 MB`.

## Installation and Usage

### Directory structure

* `dist/` holds the packaged jar file upon build completion.
* `lib/` contains the jar files required to compile the extension. These are the jar files from the JPF build.
* `src/classes/` contains the compiled source files before being packaged
* `src/examples/` contains the sample code which leverages `jmt`.
* `src/main/jmt/listener/` contains the `HeapListenerDot` listener, (as well as an internally used version of `StateSpaceDot`).
* `src/main/jmt/util/` holds the extracted `StateInformation` class from `StateSpaceDot`.
* `src/main/jmt/vm/` is the location of the `NotExceedMemory` search contraint.

### Building
Since the base JPF jar files ship with this extension, the only prerequisites are Java and Ant. To build, run `ant`, which will compile the extension and package it into a JAR file in `dist` folder.

### Installation
Place the JAR in the same directory as `RunJPF.jar`, `jpf-classes.jar` and so on. Concatenate the included `jpf.properties` in your local `~/.jpf/site.properties` file.

### Usage
The following is the list of valid options and values (with defaults):

* `listener=jmt.listener.HeapListenerDot`: A custom listener which extends `gov.nasa.jpf.StateSpaceDot` by including memory usage information. Not enabled by default.
* `jmt.report.memory_prefix=[B|KB|MB]`: Defines the unit type for the memory usage reported. Report values will be scaled according to this prefix. Defaults to kilobytes.
* `search.properties=jmt.vm.NotExceedMemory`: A custom search constraint which ensures memory usage does not cross a given threshold. Not enabled by default.
* `jmt.search.memory_units=[B|KB|MB|GB|%]`: Defines the unit type for the memory limit. Defaults to bytes.
* `jmt.search.memory_limit=n`: Defines the limit for the memory usage of the system under test. Defaults to infinity (`0`).

There are examples distributed in `src/examples`.
