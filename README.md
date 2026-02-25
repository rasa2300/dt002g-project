# DT002G Project - Note-taking application with image support
This is the source repository for a note-taking application with image support written in Java, primarily targeting desktop and laptop computers. The GUI makes use of the Swing toolkit and the storage of notes is done in a SQLite database ensuring that the data stored in the program is portable and able to be utilised by future projects.

It is a project for the DT002G Applied Computer Engineering course at Mid Sweden University and is currently a work in progress.

In order to see what is currently being worked on and gauge the progress of the project, you can [see the Kanban board used for development](https://github.com/users/rasa2300/projects/1/views/1). It contains cards corresponding to an issue on the issue tracker, detailing a unit of work that is to be picked up by a group member.

## Building and running
We use Apache Maven as our build system, which is fairly standard for Java projects. We currently target Java 21, so any version of OpenJDK 21 and above should suffice for building the project.

To get the project's source code onto your machine you can either [download a tarball](https://github.com/rasa2300/dt002g-project/archive/refs/heads/main.tar.gz) or (recommended) clone the repository using Git:

```bash
git clone https://github.com/rasa2300/dt002g-project
```

The simplest way to run the application or the unit tests would be to open the project in IntelliJ IDEA and run it from there. For building and running unit tests on the command-line, read on.

In order to build and create a JAR out of the project, run the following command:

```bash
mvn package
```

It will be available as a JAR in the `target/` repository and should be able to run (Note: We have not implemented the GUI yet!).

In order to run the test suite, run the following command:

```bash
mvn test
```

It should hopefully pass.
