package se.miun.dt002g.notes.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.miun.dt002g.notes.exceptions.NoteNotFoundException;
import se.miun.dt002g.notes.interfaces.NoteInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the NoteDatabaseModel class.
 * @author rasa2300
 */
public class NoteDatabaseModelTest {
    private static DatabaseModel dbModel;
    private static NoteDatabaseModel noteDbModel;
    private static HashMap<String, String> map;

    private static Note mapResultSetToNote(ResultSet resultSet) {
        assertNotNull(resultSet, "Result set should not be null");

        try {
            map = new HashMap<>();
            map.put("title", resultSet.getString("title"));
            map.put("content", resultSet.getString("content"));
            map.put("creationTime", resultSet.getString("creationTime"));
            map.put("modificationTime", resultSet.getString("modificationTime"));

            return new Note(map);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initial setup before running each test.
     */
    @BeforeEach
    public void setUp() {
        dbModel = new DatabaseModel("testdb_NoteDatabaseModelTest.sqlite");
        noteDbModel = new NoteDatabaseModel(dbModel.getConnection());
    }

    /**
     * Destroy the database after each tests have run such that it is clean
     * for the next run.
     */
    @AfterEach
    public void tearDown() {
        dbModel.close();
        dbModel.destroy();
    }

    /**
     * Verify that adding a note to the database works correctly.
     */
    @Test
    public void shouldAddNote() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");

        assertNotEquals(-1, noteDbModel.createNote(new Note(map)), "Inserted row ID should not be -1");
    }

    /**
     * Verify that adding and updating a note in the database works correctly.
     */
    @Test
    public void shouldAddAndUpdateNote() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");

        var note = new Note(map);
        var id = noteDbModel.createNote(note);

        map.replace("title", "title2");
        note.updateNote(map);

        assertDoesNotThrow(() -> noteDbModel.updateNote(id, note), "Note ID should exist");
    }

    /**
     * Verify that trying to update a note with an ID that does not exist throws an error, as there have not been
     * any rows affected by the query.
     */
    @Test
    public void shouldThrowExceptionWhenUpdatingNonexistentNote() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");

        var note = new Note(map);

        assertThrows(
            NoteNotFoundException.class,
            () -> noteDbModel.updateNote(100000000, note),
            "Note ID should not exist");
    }

    /**
     * Verify that the retrieval method throws an exception saying that the note could not be found, if a nonexistent
     * ID is provided to the method.
     */
    @Test
    public void shouldThrowExceptionWhenRetrievingNonexistentNote() {
        assertThrows(
            NoteNotFoundException.class,
            () -> noteDbModel.getNote(304985890, null),
            "Note shouldn't exist");
    }

    /**
     * Verify that adding a note to the database, then retrieving it using the ID that was returned works, in that
     * the same data that was put in the database can also be retrieved from the database.
     */
    @Test
    public void shouldAddAndGetNote() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");
        map.put("creationTime", "2026-01-01T00:00:01");
        map.put("modificationTime", "2026-02-01T00:00:01");

        var note = new Note(map);
        var id = noteDbModel.createNote(note);

        NoteInterface note2 = noteDbModel.getNote(id, NoteDatabaseModelTest::mapResultSetToNote);

        assertAll(
            "Asserting retrieved note data from the database",
            () -> assertEquals("title", note2.getTitle(), "Title should be 'title'"),
            () -> assertEquals("content", note2.getContent(), "Content should be 'content'"),
            () -> assertEquals("2026-01-01T00:00:01", note2.getCreationTime().toString(), "Creation time should be '2026-01-01T00:00:00'"),
            () -> assertEquals("2026-02-01T00:00:01", note2.getModificationTime().toString(), "Modification time should be '2026-02-01T00:00:00'")
        );
    }

    /**
     * Verify that adding and deleting a note in the database works correctly.
     */
    @Test
    public void shouldAddAndDeleteNote() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");

        var note = new Note(map);
        var id = noteDbModel.createNote(note);

        map.replace("title", "title2");
        note.updateNote(map);

        assertDoesNotThrow(() -> noteDbModel.deleteNote(id), "Note ID should exist");
    }

    /**
     * Verify that the delete method throws an exception saying that the note could not be found, if a nonexistent
     * ID is provided to the method.
     */
    @Test
    public void shouldThrowExceptionWhenDeletingNonexistentNote() {
        assertThrows(
            NoteNotFoundException.class,
            () -> noteDbModel.deleteNote(329847234),
            "Note shouldn't exist");
    }

    /**
     * Verify that all notes added to the database are successfully fetched.
     */
    @Test
    public void shouldFetchAllNotes() {
        map = new HashMap<>();
        map.put("title", "title");
        map.put("content", "content");

        noteDbModel.createNote(new Note(map));
        noteDbModel.createNote(new Note(map));

        List<NoteInterface> notes = noteDbModel.getNotes(NoteDatabaseModelTest::mapResultSetToNote);

        assertEquals(2, notes.size(), "Should only contain 2 notes");
    }
}
