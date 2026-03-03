package se.miun.dt002g.notes.models;

import se.miun.dt002g.notes.exceptions.NoteNotFoundException;
import se.miun.dt002g.notes.interfaces.NoteInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class which implements a layer for interacting with the database for persistent note storage.
 * @author rasa2300
 */
public class NoteDatabaseModel {
    private final Connection connection;

    /**
     * Constructor method
     * @param connection SQLite database connection
     */
    public NoteDatabaseModel(Connection connection) {
        this.connection = connection;
        initialiseTable();
    }

    /**
     * Initialises the notes table, defining the schema of the table and creating it if it does not already exist.
     */
    private void initialiseTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS notes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT,
                content TEXT,
                creationTime TEXT,
                modificationTime TEXT
            )""";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a note row in the database from a note object
     * @param note A note object implementing the NoteInterface
     * @return The ID that the inserted row was given in the database
     */
    public long createNote(NoteInterface note) {
        String sql = """
                INSERT INTO notes (title, content, creationTime, modificationTime)
                VALUES (?,?,?,?)
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setString(3, note.getCreationTime().toString());
            pstmt.setString(4, note.getModificationTime().toString());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                // Assumed that this can't actually fail
                generatedKeys.next();
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update a note with a given database row ID in the table.
     * @param id Numeric ID of the note
     * @param note A note object implementing the NoteInterface
     * @throws NoteNotFoundException If note was not found.
     */
    public void updateNote(long id, NoteInterface note) {
        String sql = """
                UPDATE notes SET title = ?, content = ?, creationTime = ?, modificationTime = ?
                WHERE id = ?""";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, note.getTitle());
            pstmt.setString(2, note.getContent());
            pstmt.setString(3, note.getCreationTime().toString());
            pstmt.setString(4, note.getModificationTime().toString());
            pstmt.setLong(5, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0)
                throw new NoteNotFoundException("No note with that ID was found. No rows have been affected.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve a note from the database
     * @param id Numeric ID of the note
     * @param callback Callback function for populating row data into a NoteInterface object
     * @throws NoteNotFoundException If note was not found.
     * @return A note object implementing the NoteInterface
     */
    public NoteInterface getNote(long id, ResultSetCallback callback) {
        String sql = """
                SELECT id, title, content, creationTime, modificationTime
                FROM notes WHERE id = ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                return callback.process(rs);
            else
                throw new NoteNotFoundException("No such note with that ID.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve all notes.
     * @param callback Callback function for populating row data into a NoteInterface object
     * @return A list of note objects implementing the NoteInterface
     */
    public List<NoteInterface> getNotes(ResultSetCallback callback) {
        String sql = """
                SELECT id, title, content, creationTime, modificationTime
                FROM notes
                ORDER BY modificationTime DESC""";
        List<NoteInterface> notes = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
                notes.add(callback.process(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notes;
    }

    /**
     * Interface for the callback used when receiving data about a note row in the database.
     */
    public interface ResultSetCallback {
        /**
         * Process the result into a NoteInterface compatible object
         * @param resultSet Result set object from a database query
         * @return Note object implementing the NoteInterface
         */
        NoteInterface process(ResultSet resultSet);
    }

    /**
     * Delete a note from the database.
     * @param id Numeric ID of the note
     * @throws NoteNotFoundException If note was not found.
     */
    public void deleteNote(long id) {
        String sql = """
                DELETE FROM notes WHERE id = ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0)
                throw new NoteNotFoundException("No note with that ID was found. No rows have been affected.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
