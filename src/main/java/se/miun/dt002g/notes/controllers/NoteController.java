package se.miun.dt002g.notes.controllers;

import se.miun.dt002g.notes.interfaces.ButtonViewInterface;
import se.miun.dt002g.notes.interfaces.ListViewInterface;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;
import se.miun.dt002g.notes.interfaces.NoteViewInterface;

import java.util.List;

/**
 * Controller class for note handling
 * @author cabr2300
 */
public class NoteController implements NoteControllerInterface {

    private ListViewInterface listView;
    private NoteViewInterface noteView;
    private ButtonViewInterface buttonView;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListView(ListViewInterface listView) {
        this.listView = listView;
        //TODO get the notes in separate thread
        //TODO call listView.showNotes(notes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNoteView(NoteViewInterface noteView) {
        this.noteView = noteView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setButtonView(ButtonViewInterface buttonView) {
        this.buttonView = buttonView;
        buttonView.noteSelected(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createNote() {
        noteView.initiateNewNote();
        buttonView.noteSelected(true);
        buttonView.editingNote(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editNote() {
        noteView.toggleEditable(true);
        buttonView.editingNote(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNote() {
        //TODO check Model if a note is selected. If not, return

        boolean confirmed = buttonView.showDeleteConfirmation();

        if (confirmed) {
            buttonView.noteSelected(false);
            noteView.clearNote();
            //TODO have listView unselect and refresh
            //TODO tell Model to delete note
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveNote() {
        List<String> noteContent = noteView.getNoteContent();
        noteView.toggleEditable(false);
        buttonView.editingNote(false);

        //TODO call Model to update or save new Note in separate thread
        //TODO call listView.showNotes() with the updated notes
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNoteSelected(long noteId) {
        //TODO get the note from model
        //TODO call noteView.displayNote()
        buttonView.noteSelected(true);
    }
}
