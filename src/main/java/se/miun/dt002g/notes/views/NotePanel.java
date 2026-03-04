package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.NoteViewInterface;
import se.miun.dt002g.notes.models.Note;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * View class representing the panel displaying a single note
 * @author cabr2300
 */
public class NotePanel extends RoundedPanel implements NoteViewInterface {

    private final JTextField titleField;
    private final JTextArea contentArea;
    JScrollPane scrollPane;

    /**
     * Class constructor setting up the structure.
     */
    public NotePanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        titleField = new JTextField("");
        titleField.setFont(AppConfig.NOTE_TITLE_FONT);
        titleField.setBackground(AppConfig.PANEL_COLOR);

        contentArea = new JTextArea("");
        contentArea.setFont(AppConfig.NOTE_CONTENT_FONT);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(contentArea);
        scrollPane.setBackground(AppConfig.PANEL_COLOR);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(titleField, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        toggleVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayNote(Note note) {
        titleField.setText(note.getTitle());
        contentArea.setText(note.getContent());
        toggleEditable(false);
        toggleVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearNote() {
        titleField.setText("");
        contentArea.setText("");
        toggleVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getNoteContent() {
        List<String> noteContent = new ArrayList<>();
        noteContent.add(titleField.getText());
        noteContent.add(contentArea.getText());
        return noteContent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleEditable(boolean isEditable) {
        titleField.setEditable(isEditable);
        contentArea.setEditable(isEditable);
        if (isEditable) {
            titleField.setBorder(UIManager.getBorder("TextField.border"));
            scrollPane.setBorder((UIManager.getBorder("ScrollPane.border")));
        } else {
            titleField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initiateNewNote() {
        clearNote();
        toggleVisible(true);
        toggleEditable(true);
    }

    /**
     * Toggle between visible and hidden title and content area.
     * @param isVisible is true if the areas should be visible, otherwise false
     */
    private void toggleVisible(boolean isVisible) {
        titleField.setVisible(isVisible);
        scrollPane.setVisible(isVisible);
    }
}
