package se.miun.dt002g.notes.interfaces;

import java.sql.Connection;

/**
 * Interface defining methods to manage a database connection.
 * @author rasa2300
 */
public interface DatabaseConnectionInterface {

    /**
     * Opens a new SQLite connection, creating a new database if necessary.
     */
    void open();

    /**
     * Returns the current status on the database connection, whether it is open or has been closed.
     * @return Is connection open?
     */
    boolean isConnectionOpen();

    /**
     * Get a reference to the current database connection.
     * @return SQLite connection handle
     */
    Connection getConnection();

    /**
     * Closes the current SQLite connection.
     * @throws RuntimeException if an error occurred during closing.
     */
    void close();

    /**
     * Delete the entire database file. Connection needs to be closed first before calling.
     * <p>
     * WARNING: This is a destructive action! It will irreversibly destroy all data stored in the database and should be
     * used when all data should be removed to create a clean state.
     * @return The result of the file deletion operation - True if the file was deleted, otherwise false
     */
    boolean destroy();
}
