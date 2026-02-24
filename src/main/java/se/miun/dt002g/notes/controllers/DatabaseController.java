package se.miun.dt002g.notes.controllers;

import se.miun.dt002g.notes.interfaces.DatabaseConnectionInterface;
import se.miun.dt002g.notes.models.DatabaseModel;

import java.sql.Connection;

/**
 * Controller for the database, initialises a database connection and provides methods for interacting with the underlying
 * database storage (not yet implemented).
 * @author rasa2300
 */
public class DatabaseController implements DatabaseConnectionInterface {
    private final DatabaseModel databaseModel;

    /**
     * Constructor method
     * @param dbFile Database SQLite file
     */
    public DatabaseController(String dbFile) {
        this.databaseModel = new DatabaseModel(dbFile);
    }

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
}
