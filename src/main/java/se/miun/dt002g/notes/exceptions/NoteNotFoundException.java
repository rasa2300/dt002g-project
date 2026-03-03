package se.miun.dt002g.notes.exceptions;

/**
 * Exception to be thrown in note retrieval handling when a note could not be found
 * in the database.
 * @author rasa2300
 */
public class NoteNotFoundException extends RuntimeException {
    /**
     * Constructor method
     * @param message Exception message
     */
    public NoteNotFoundException(String message) {
        super(message);
    }
}
