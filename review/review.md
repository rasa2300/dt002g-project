# Repository Review — Group 4

**Course:** Applied Datateknik, Mid Sweden University  
**Group:** 4  
**Project name:** Note-taking Application with Image Support  
**Repository:** https://github.com/rasa2300/dt002g-project  
**Review date:** 2026-03-04  
**Reviewer:** GitHub Copilot (automated evidence-based review) Claude Sonnet 4.6 
---

## 1. Repository Summary

### Project purpose
A desktop note-taking application with image support, targeting laptop/desktop users. Notes are stored in a local SQLite database. The GUI is built with Java Swing.

### Technology stack
Java 21 (compiled to Java 17), Apache Maven, Java Swing, SQLite via `sqlite-jdbc` 3.49.1.0, JUnit Jupiter 5.7.0, AssertJ Swing for GUI tests.

### Current development state
Work in progress. The backend data layer (database connection, note model, full CRUD operations in `NoteDatabaseModel`) is implemented and tested. The GUI skeleton exists (`MainFrame`, `ButtonPanel`, `ListPanel`, `NotePanel`, `RoundedPanel`, `CustomButton`), but all `NoteController` methods are TODO stubs and the note display panel is empty. The application's `main()` only prints `"helo"` — it does not launch the GUI. Image support has not been started.

---

## 2. Evidence-Based Checklist of Good Practices

Scale: **Yes / Partly / No / Unclear**

### 2.1 Structure and organization

**The repository has a clear and logical folder structure.**  
Assessment: Yes  
Evidence: Standard Maven layout (`src/main/java`, `src/test/java`). Source packages are `controllers`, `models`, `views`, `interfaces`, `exceptions`, `config` under `se.miun.dt002g.notes`.  
Comment: The package hierarchy directly reflects the MVC architecture.

**Source code, tests, configuration, and documentation are separated appropriately.**  
Assessment: Yes  
Evidence: Production code under `src/main/java`, tests under `src/test/java`, build configuration in `pom.xml`, documentation in `README.md`. No test code mixed into main.  
Comment: Follows Maven conventions correctly.

**File and folder names are meaningful and consistent.**  
Assessment: Yes  
Evidence: `DatabaseModel.java`, `NoteDatabaseModel.java`, `NoteController.java`, `DatabaseController.java`, `NotePanel.java`, `ListPanel.java` — all names are descriptive and consistent with their role.  
Comment: No ambiguous or generic names observed.

**The repository avoids unnecessary generated files or clutter.**  
Assessment: Yes  
Evidence: `.gitignore` excludes `target/`, `*.class`, `*.jar`, and IDE files. The `target/` folder present locally is not tracked by git.  
Comment: Gitignore is comprehensive and well-structured.

### 2.2 Code quality

**The code appears correct and runnable.**  
Assessment: Partly  
Evidence: `pom.xml` configures an executable JAR with main class `se.miun.dt002g.notes.Project`. However, `Project.java` only prints `"helo"` — the GUI is not launched. The data layer logic looks functionally correct.  
Comment: README itself states "We have not implemented the GUI yet".

**The code follows consistent style and coding conventions.**  
Assessment: Yes  
Evidence: Javadoc on all public methods and classes (with `@author`, `@param`, `@return`). Uniform indentation, access modifiers, and use of `final`. Utility classes (`Project`, `AppConfig`) correctly throw `IllegalStateException` on instantiation.  
Comment: Style is notably consistent across files authored by both contributors.

**The code is readable and reasonably modular.**  
Assessment: Yes  
Evidence: MVC is applied throughout — `controllers`, `models`, `views` are separate packages. Interface-driven design decouples components: `NoteInterface`, `NoteControllerInterface`, `DatabaseConnectionInterface`, `NoteViewInterface`, `ListViewInterface`. Classes are small and focused.  
Comment: One of the stronger aspects of the codebase.

**There are no major obvious code smells (duplication, overly large files, unclear naming).**  
Assessment: Yes  
Evidence: No duplicated SQL logic; `NoteDatabaseModel` owns all note-level DB operations, `DatabaseModel` owns connection management only. Files range from ~50–190 lines; none are bloated.  
Comment: No significant smells. `NoteController` consists entirely of TODO stubs, making it currently dead code, but that is a completeness issue rather than a smell.

### 2.3 Documentation

**The repository contains a clear README.**  
Assessment: Yes  
Evidence: `README.md` explains project purpose, tech stack, Kanban board link, prerequisites (Java 21, Maven), and build/run/test commands.  
Comment: Well-written and informative for a course project.

**The README explains how to install, run, and use the system.**  
Assessment: Yes  
Evidence: `mvn package` to build JAR, `mvn test` to run tests, `git clone` instructions and link to tarball download. Java version requirement stated.  
Comment: README accurately notes the GUI is not yet implemented.

**The documentation would help a new developer understand and contribute to the project.**  
Assessment: Yes  
Evidence: Kanban board link provided. Javadoc on all public classes and methods. README explains architecture direction.  
Comment: A developer could orient themselves quickly.

**Important design decisions or setup details are documented.**  
Assessment: Partly  
Evidence: Choice of SQLite for portability mentioned in the README. MVC structure implied by package layout but not explicitly described.  
Comment: A brief architecture section in the README would strengthen this.

### 2.4 Testing

**The repository contains tests.**  
Assessment: Yes  
Evidence: 6 test files: `ProjectTest.java`, `NoteTest.java`, `DatabaseModelTest.java`, `NoteDatabaseModelTest.java`, `DatabaseControllerTest.java`, `MainFrameTest.java`.  
Comment: Good coverage of the data layer.

**Tests are relevant to the main functionality.**  
Assessment: Yes  
Evidence: `NoteDatabaseModelTest` tests `createNote`, `updateNote`, `deleteNote`, `getNote`, `getAllNotes` including edge cases (e.g., exception on updating a non-existent note). Uses `@BeforeEach`/`@AfterEach` to set up and tear down a real in-process SQLite database.  
Comment: Tests target the core data operations and are well-designed.

**Tests can be executed with clear instructions.**  
Assessment: Yes  
Evidence: README states `mvn test`. Standard Maven test runner (`maven-surefire-plugin`) is configured in `pom.xml`.  
Comment: No unusual setup required.

**Tests appear to pass, or there is evidence that they have been run successfully.**  
Assessment: Unclear  
Evidence: Tests are well-formed with proper setup/teardown but were not executed during this review. No CI badge or test result artifact present.  
Comment: The test code looks sound; actual pass/fail status cannot be confirmed without running the build.

### 2.5 Collaboration and development practices

**Commit history suggests incremental development.**  
Assessment: Yes  
Evidence: 11 commits over feature branches (`feature/databasemodel`, `feature/notemodel`, `feature/notedatabasemodel`, `feature/layout`), merged via pull requests (#3, #5, #8, #9). Each PR corresponds to a meaningful unit of work.  
Comment: Development process is clearly structured.

**Commit messages are meaningful.**  
Assessment: Yes  
Evidence: Examples: `"Implement NoteDatabaseModel and wire up to DatabaseController"`, `"Created the basic layout of the GUI"`, `"Don't hardcode forward slashes in filepaths"`, `"Initial implementation of Note model"`.  
Comment: Messages describe what was done. No empty or throwaway messages.

**There is evidence of collaboration between both students.**  
Assessment: Yes  
Evidence: Two distinct authors in git log: `rasa2300 <rasa2300@student.miun.se>` (models, database layer) and `Carl Sebastian Broberg <cabr2300@student.miun.se>` (GUI views, controllers). `@author` tags confirm the division.  
Comment: Division of labor is clear and complementary.

---

## 3. What Is Being Done Well

1. Clean MVC architecture with interface-driven design — components are properly decoupled and the package structure mirrors the architecture.
2. The database layer is fully implemented: connection management, schema initialisation, and all CRUD operations for notes with appropriate custom exceptions (`NoteNotFoundException`, `DatabaseInitException`).
3. Tests for the data layer are thorough and well-structured, using proper `@BeforeEach`/`@AfterEach` with a live in-process SQLite database.
4. Comprehensive Javadoc on all public classes and methods across both contributors' code.
5. Collaborative workflow using feature branches and pull requests is consistently maintained throughout the commit history.

---

## 4. What Needs Improvement

1. `main()` in `Project.java` only prints `"helo"` — the application cannot be run; `MainFrame` is never instantiated from the entry point.
2. All `NoteController` methods (`createNote`, `editNote`, `deleteNote`, `saveNote`, `onNoteSelected`) are empty TODO stubs — no GUI action is wired to any functionality.
3. `NotePanel.displayNote()` and `getNoteContent()` are TODO stubs — the note editing view is non-functional.
4. Image support (FR-5, FR-6, FR-7) has not been started; no image-related code or planned structure exists anywhere in the repository.
5. There is no CI integration; tests results cannot be confirmed from the repository without manually running the build.

---

## 5. Evaluation Against Requirements

### 5.1 Functional Requirements

| Requirement | Description | Status | Evidence | Comment |
|---|---|---|---|---|
| FR-1 | Save notes entered by the user. | Partly | `NoteDatabaseModel.createNote()` and `DatabaseController` implemented; `NoteController.saveNote()` is a TODO stub; no GUI wiring. | Backend ready; GUI not connected. |
| FR-2 | Display a list of saved notes. | Partly | `ListPanel.showNotes()` and `addNoteToList()` implemented with sorting and click listeners; no data is fed into the list at runtime. | View code exists; controller does not load notes from DB. |
| FR-3 | Edit an existing note. | Partly | `NoteDatabaseModel.updateNote()` implemented; `NoteController.editNote()` is a TODO stub; `NotePanel.getNoteContent()` returns an empty map. | Backend ready; GUI not connected. |
| FR-4 | Delete a note. | Partly | `NoteDatabaseModel.deleteNote()` implemented; `NoteController.deleteNote()` is a TODO stub. | Backend ready; GUI not connected. |
| FR-5 | Add an image to a note. | No | No image-related code found anywhere in the repository. | Not started. |
| FR-6 | Delete an image added to a note. | No | No image-related code found anywhere in the repository. | Not started. |
| FR-7 | Resize an image. | No | No image-related code found anywhere in the repository. | Not started. |
| FR-8 | Run as an executable file. | No | `pom.xml` configures an executable JAR with mainClass `se.miun.dt002g.notes.Project`; however, `main()` only prints `"helo"` and does not launch the GUI. | JAR structure is configured; application is not runnable. |

### 5.2 Non-Functional Requirements

| Requirement | Description | Status | Evidence | Comment |
|---|---|---|---|---|
| NFR-1 | Response time below 0.1 second. | Unclear | Application is not functional; no runtime measurements possible. | Cannot assess. |
| NFR-2 | Image loading time below 1 second. | Unclear | Image feature not implemented. | Not applicable. |
| NFR-3 | Be usable without training. | Partly | GUI scaffold has clearly labelled buttons ("new note", "edit note", "save note", "delete note") and a search field with a tooltip; application is not functional. | UI design intent is appropriate; functionality absent. |
| NFR-4 | Be usable while offline. | Partly | SQLite storage is local; no network dependencies in the codebase. Application is not functional. | Architecture supports offline use; functionality absent. |

---

## 6. Overall Assessment

### Summary judgment
The project demonstrates solid engineering foundations: the backend data layer is well-implemented and tested, the architecture follows MVC with proper interfaces, and the collaboration pattern is clear and systematic. However, as of the review date the application is non-functional as a user-facing product. The bridge between the GUI layer and the data layer has not been built (`NoteController` is entirely un-implemented), the entry point does not launch the GUI, and image support — a core stated requirement — has not been started.

### Confidence in this review
Medium. Based entirely on static code reading and git log analysis. Tests were not executed; the JAR was not built or run.

### Limitations of this review
Tests may pass or fail in ways not detectable by code reading. Runtime behaviour of the GUI scaffold cannot be assessed. The Kanban board referenced in the README may indicate planned work not yet reflected in the code.

---

## 7. Suggested Improvements

1. Connect `NoteController` to `DatabaseController` so that creating, saving, editing, and deleting notes actually persist data via the already-implemented DB layer.
2. Implement `NotePanel.displayNote()` and `getNoteContent()` so that a selected note can be shown and edited in the UI.
3. Update `Project.main()` to instantiate and display `MainFrame` so the application can actually be launched.
4. Begin implementation of image support — even a basic ability to attach an image file path to a note would satisfy FR-5 and give FR-6/FR-7 a foundation.
5. Add a CI workflow (e.g., GitHub Actions with `mvn test`) so that test results are automatically reported and visible in the repository.
