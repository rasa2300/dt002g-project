package se.miun.dt002g.notes.exceptions;

/**
 * Exception to be thrown when an error occurs during the initialisation of the database connection.
 * @author rasa2300
 */
public class DatabaseInitException extends RuntimeException {
    /**
     * Constructor method
     * @param message Exception message
     */
    public DatabaseInitException(String message) {
        super(message);
    }
}
