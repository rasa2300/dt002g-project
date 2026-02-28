package se.miun.dt002g.notes.interfaces;

import java.util.List;

import se.miun.dt002g.notes.models.Note;

/**
 * Interface of the ListPanel class
 * Used by the NoteController
 * @author cabr2300
 */
public interface ListViewInterface {

    /**
     * Display a scrollpane with the titles of the notes
     * @param notes is a list of Note objects
     */
    void showNotes(List<Note> notes);
}
