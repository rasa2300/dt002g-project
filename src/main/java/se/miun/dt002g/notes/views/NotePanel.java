package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.interfaces.NoteControllerInterface;
import se.miun.dt002g.notes.interfaces.NoteViewInterface;
import se.miun.dt002g.notes.models.Note;

import java.util.HashMap;
import java.util.Map;

/**
 * View class representing the panel displaying a single note
 * @author cabr2300
 */
public class NotePanel extends RoundedPanel implements NoteViewInterface {

    NoteControllerInterface noteController;

    /**
     * Class constructor assigning the controller
     * @param noteController is the interface of the NoteController
     */
    public NotePanel(NoteControllerInterface noteController) {
        this.noteController = noteController;
    }

    /**
     * {@inheritDoc}
     */
    public void displayNote(Note note) {
        //TODO implement method
    }

    /**
     * {@inheritDoc}
     */
    public void clearNote() {
        this.removeAll();
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> getNoteContent() {
        //TODO implement method
        return new HashMap<>();
    }
}
