package se.miun.dt002g.notes.controllers;

import se.miun.dt002g.notes.interfaces.ListViewInterface;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;
import se.miun.dt002g.notes.interfaces.NoteViewInterface;

/**
 * Controller class for note handling
 * @author cabr2300
 */
public class NoteController implements NoteControllerInterface {

    private ListViewInterface listView;
    private NoteViewInterface noteView;

    /**
     * {@inheritDoc}
     */
    public void setListView(ListViewInterface listView) {
        this.listView = listView;
    }

    /**
     * {@inheritDoc}
     */
    public void setNoteView(NoteViewInterface noteView) {
        this.noteView = noteView;
    }

    /**
     * {@inheritDoc}
     */
    public void createNote() {
        //TODO implement method
    }

    /**
     * {@inheritDoc}
     */
    public void editNote() {
        //TODO implement method
    }

    /**
     * {@inheritDoc}
     */
    public void deleteNote() {
        //TODO implement method
    }

    /**
     * {@inheritDoc}
     */
    public void saveNote() {
        //TODO implement method
    }

    /**
     * {@inheritDoc}
     */
    public void onNoteSelected(long noteId) {
        //TODO implement method
    }
}
