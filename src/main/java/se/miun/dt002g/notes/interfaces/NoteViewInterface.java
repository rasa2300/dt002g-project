package se.miun.dt002g.notes.interfaces;

import se.miun.dt002g.notes.models.Note;

import java.util.List;

/**
 * Interface of the NotePanel class.
 * @author cabr2300
 */
public interface NoteViewInterface {

    /**
     * Display the information of a Note.
     * @param note is the Note object.
     */
    void displayNote(Note note);

    /**
     * Remove all information of a note from the panel.
     */
    void clearNote();

    /**
     * Get the information of a note displayed in the panel
     * @return a list with a title key and a content value
     */
    List<String> getNoteContent();

    /**
     * Switch between the selected note bing editable or view only.
     * @param isEditable is true if the note should be editable, otherwise false
     */
    void toggleEditable(boolean isEditable);

    /**
     * Display empty text fields for creating a new note
     */
    void initiateNewNote();
}
