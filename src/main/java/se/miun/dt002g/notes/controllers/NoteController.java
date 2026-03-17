package se.miun.dt002g.notes.controllers;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.exceptions.NoteNotFoundException;
import se.miun.dt002g.notes.interfaces.*;
import se.miun.dt002g.notes.models.ImageHandler;
import se.miun.dt002g.notes.models.ImageResizer;
import se.miun.dt002g.notes.models.Note;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Controller class for note handling
 * @author cabr2300
 */
public class NoteController implements NoteControllerInterface {

    private ListViewInterface listView;
    private NoteViewInterface noteView;
    private ButtonViewInterface buttonView;
    private final ExecutorService executors;
    private DatabaseController database;
    private ImageHandler imageHandler;
    private ImageResizer resizer;

    /**
     * Class constructor.
     * Instantiates a fixed thread pool with two threads.
     * Instantiates the database and image handler on a separate thread before returning.
     * Failure to instantiate the necessary classes causes exception.
     */
    public NoteController() {
        executors = Executors.newFixedThreadPool(2);
        Future<?> init = executors.submit(() -> {
            this.database = new DatabaseController(AppConfig.DB_FILE_NAME);
            this.imageHandler = new ImageHandler();
            this.resizer = new ImageResizer();
        });
        try {
            init.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListView(ListViewInterface listView) {
        this.listView = listView;
        executors.submit(() -> {
            List<NoteInterface> notes = loadNotesOrFallback();
            SwingUtilities.invokeLater(() -> listView.showNotes(notes));
        });
    }

    /**
     * Helper method to setListView().
     * Tries to get the notes from the database.
     * If it fails, creates a placeholder note that explains the problem.
     * @return a HashMap containing NoteInterfaces.
     */
    private List<NoteInterface> loadNotesOrFallback() {
        try {
            return database.getNotes();
        } catch (RuntimeException e) {
            NoteInterface fallback = new Note((HashMap<String, String>) Map.of(
                    "title", AppConfig.TITLE_CANNOT_LOAD_NOTES,
                    "content", AppConfig.CONTENT_CANNOT_LOAD_NOTES
            ));
            return List.of(fallback);
        }
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
        boolean confirmed = buttonView.showDeleteConfirmation();
        if (confirmed) {
            Note note = noteView.getNote();
            buttonView.noteSelected(false);
            noteView.clearNote();
            executors.submit(() -> {
                imageHandler.deleteImage(note.getId());
            });
            executors.submit(() -> {
                try {
                    database.deleteNote(note.getId());
                } catch (NoteNotFoundException ignored) {}
                List<NoteInterface> notes = loadNotesOrFallback();
                SwingUtilities.invokeLater(() -> listView.showNotes(notes));
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveNote() {
        Note note = noteView.getNote();
        noteView.toggleEditable(false);
        buttonView.editingNote(false);
        executors.submit(() -> {
            try {
                database.updateNote(note.getId(), note);
                saveOrDeleteImage(note, note.getId());
            } catch (NoteNotFoundException e) {
                long id = database.createNote(note);
                saveOrDeleteImage(note, id);
            }
            List<NoteInterface> notes = loadNotesOrFallback();
            SwingUtilities.invokeLater(() -> listView.showNotes(notes));
        });
    }

    /**
     * Helper method to saveNote. Resizes and saves an image if attached to note.
     * If the note has no image, attempts to delete any prior image associate with it.
     * @param note is a Note object
     * @param id is the unique id of a note
     */
    private void saveOrDeleteImage(Note note, long id) {
        if(note.getImage() != null) {
            BufferedImage image = resizer.resizeImage(note.getImage());
            imageHandler.saveImage(image, id);
        } else {
            imageHandler.deleteImage(id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNoteSelected(long noteId) {
        executors.submit(() -> {
            Note note = loadNoteOrFallback(noteId);
            BufferedImage image = imageHandler.getImage(noteId);
            note.addImage(image);
            SwingUtilities.invokeLater(() -> {
                buttonView.noteSelected(true);
                buttonView.editingNote(false);
                noteView.displayNote(note);
            });
        });
    }

    /**
     * Helper method to onNoteSelected()
     * Gets the note from the database,
     * or falls back to creating a new note.
     * @param noteId is the unique identifier of the note
     * @return a Note object.
     */
    private Note loadNoteOrFallback(long noteId) {
        try {
            return database.getNote(noteId);
        } catch (NoteNotFoundException e) {
            return new Note(new HashMap<>());
        }
    }
}
