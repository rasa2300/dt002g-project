package se.miun.dt002g.notes.controllers;

import se.miun.dt002g.notes.exceptions.NoteNotFoundException;
import se.miun.dt002g.notes.interfaces.DatabaseConnectionInterface;
import se.miun.dt002g.notes.interfaces.NoteInterface;
import se.miun.dt002g.notes.models.DatabaseModel;
import se.miun.dt002g.notes.models.Note;
import se.miun.dt002g.notes.models.NoteDatabaseModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for the database, initialises a database connection and provides methods for interacting with the underlying
 * database storage (not yet implemented).
 * @author rasa2300
 */
public class DatabaseController implements DatabaseConnectionInterface {
    private final DatabaseModel databaseModel;
    private final NoteDatabaseModel noteDatabaseModel;

    /**
     * Constructor method
     * @param dbFile Database SQLite file
     */
    public DatabaseController(String dbFile) {
        this.databaseModel = new DatabaseModel(dbFile);
        this.noteDatabaseModel = new NoteDatabaseModel(this.getConnection());
    }

    // ---
    // Low-level SQLite database connection methods, surfaced from DatabaseModel

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        this.databaseModel.open();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnectionOpen() {
        return this.databaseModel.isConnectionOpen();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() {
        return this.databaseModel.getConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.databaseModel.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean destroy() {
        return this.databaseModel.destroy();
    }

    // ---
    // Higher-level database interaction methods for notes, surfaced from NoteDatabaseModel

    /**
     * Create a note row in the database from a note object
     * @param note A note object implementing the NoteInterface
     * @return The ID that the inserted row was given in the database
     */
    public long createNote(NoteInterface note) {
        return noteDatabaseModel.createNote(note);
    }

    /**
     * Update a note with a given database row ID in the table.
     * @param id Numeric ID of the note
     * @param note A note object implementing the NoteInterface
     * @throws NoteNotFoundException If note was not found.
     */
    public void updateNote(long id, NoteInterface note) {
        noteDatabaseModel.updateNote(id, note);
    }

    /**
     * Helper to convert a ResultSet row into a NoteInterface, used for methods
     * that retrieve notes from the database and return them as Note objects.
     */
    private NoteInterface noteFromResultSet(ResultSet resultSet) {
        try {
            var map = new HashMap<String, String>();
            map.put("id", String.valueOf(resultSet.getLong("id")));
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
     * Retrieve a note from the database
     * @param id Numeric ID of the note
     * @throws NoteNotFoundException If note was not found.
     * @return A note object implementing the NoteInterface
     */
    public NoteInterface getNote(long id) {
        return noteDatabaseModel.getNote(id, this::noteFromResultSet);
    }

    /**
     * Retrieve all notes
     * @throws RuntimeException If a SQL error occurs
     * @return A list of note objects implementing the NoteInterface
     */
    public List<NoteInterface> getNotes() {
        return noteDatabaseModel.getNotes(this::noteFromResultSet);
    }

    /**
     * Delete a note from the database.
     * @param id Numeric ID of the note
     * @throws NoteNotFoundException If note was not found.
     */
    public void deleteNote(long id) {
        noteDatabaseModel.deleteNote(id);
    }
}
