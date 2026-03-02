package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.ListViewInterface;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;
import se.miun.dt002g.notes.models.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

/**
 * View class representing the panel that shows a list of notes
 * @author cabr2300
 */
public class ListPanel extends RoundedPanel implements ListViewInterface {

    private final JTextField searchField;
    private final JScrollPane scrollPane;
    private final JPanel noteListPanel;
    private final NoteControllerInterface noteController;

    /**
     * Class constructor assigning the controller interface
     * and setting up the structure.
     * @param noteController is the interface of the NoteController
     */
    public ListPanel(NoteControllerInterface noteController) {
        this.noteController = noteController;
        this.setPreferredSize(new Dimension(AppConfig.LIST_PANEL_WIDTH, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        searchField = new JTextField();
        searchField.setToolTipText(AppConfig.SEARCH_TOOLTIP);
        searchField.setMaximumSize(new Dimension((AppConfig.LIST_PANEL_WIDTH-10), searchField.getPreferredSize().height));
        noteListPanel = new JPanel();
        noteListPanel.setLayout(new BoxLayout(noteListPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(noteListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(searchField);
        this.add(scrollPane);
    }

    /**
     *{@inheritDoc}
     */
    public void showNotes(List<Note> notes) {
        noteListPanel.removeAll();
        notes.sort(Comparator.comparing(Note::getModificationTime));
        notes.forEach(this::addNoteToList);
    }

    /**
     * Creates a JLabel and sets a Note titel as its text.
     * Adds a mouse click listener to the JLabel and adds it to the note list panel.
     * @param note is a Note object.
     */
    private void addNoteToList(Note note) {
        JLabel label = new JLabel();
        label.setText(note.getTitle());
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                noteController.onNoteSelected(note.getId());
            }
        });
        noteListPanel.add(label);
    }
}
