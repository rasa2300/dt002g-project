package se.miun.dt002g.notes.interfaces;

import java.util.List;

/**
 * Interface of the ListPanel class
 * Used by the NoteController
 * @author cabr2300
 */
public interface ListViewInterface {

    /**
     * Display a scroll pane with the titles of the notes
     * @param notes is a list of Note objects
     */
    void showNotes(List<NoteInterface> notes);
}
