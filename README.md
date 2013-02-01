TVScheduler
===========

Sheduling application for home theater machines.

Populate a Schedule object using the user interface which can then be serialized and used with the main scheduling program to automatically play the next unwatched episode of a show at the specified day and time, every week.

Written in Java, interface created using the Swing framework.

Dependencies
============
Requires Apache Commons IO 2.4 libraries
http://commons.apache.org/io/

Use
===
- Compile.

- Run the TVForm class.

- Load your desired shows into the schedule specifying the root directory that contains the shows video files. 

- Place your shows into the scheduling table.

- Serialize(save) the schedule.

- Run the TVRun class with the serialized schedule as a parameter.
