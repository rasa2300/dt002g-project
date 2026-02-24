package se.miun.dt002g.notes.models;

import se.miun.dt002g.notes.exceptions.DatabaseInitException;
import se.miun.dt002g.notes.interfaces.DatabaseConnectionInterface;

import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Model class for creating a SQLite database connection from a file in the local filesystem
 * @author rasa2300
 */
public class DatabaseModel implements DatabaseConnectionInterface {
    private Connection connection;
    private String dbPath;

    /**
     * Constructor method
     * @param dbFile SQLite database file
     */
    public DatabaseModel(String dbFile) {
        setDbPath(dbFile);
        open();
    }

    /**
     * Sets the database path based on a filename, constructing the full path to it.
     * @param dbFile Database filename
     */
    private void setDbPath(String dbFile) {
        String jarLocation;
        try {
            jarLocation = new File(DatabaseModel.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.dbPath = jarLocation + "/" + dbFile;
    }

    /**
     * Get path to the database file
     * @return Database file path
     */
    private String getDbPath() {
        return this.dbPath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + getDbPath());
        } catch (SQLException e) {
            throw new DatabaseInitException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnectionOpen() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            // Let's be realistic and say if something somehow goes wrong here,
            // the connection is definitively not open.
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (this.connection == null)
            return;

        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean destroy() {
        try {
            if (!this.connection.isClosed())
                throw new IllegalStateException("Database connection needs to be closed before destroying.");

            if (this.dbPath == null)
                throw new IllegalStateException("Database path is not initialised, aborting destruction for safety.");

            File dbFile = new File(this.dbPath);

            if (dbFile.exists())
                return dbFile.delete();
            else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
