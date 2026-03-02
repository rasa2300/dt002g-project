package se.miun.dt002g.notes.interfaces;

/**
 * Interface of the NoteController.
 * Used by View classes.
 * @author cabr2300
 */
public interface NoteControllerInterface {

    /**
     * Set the View instance that handles the list of notes
     * Called by the MainFrame
     * @param listView is the interface of that class
     */
    void setListView(ListViewInterface listView);

    /**
     * Set the View instance that shows the contents of a note
     * Called by the MainFrame
     * @param noteView is the interface of that class
     */
    void setNoteView(NoteViewInterface noteView);

    /**
     * Create a new note.
     * Called by the ButtonPanel
     */
    void createNote();

    /**
     * Edit an existing note
     * Called by the ButtonPanel
     */
    void editNote();

    /**
     * Delete a note.
     * Called by the ButtonPanel
     */
    void deleteNote();

    /**
     * Save a note.
     * Called by the ButtonPanel
     */
    void saveNote();

    /**
     * The action caused by a note in the ListPanel being clicked.
     * @param noteId the unique ID of the note.
     */
    void onNoteSelected(long noteId);
}
