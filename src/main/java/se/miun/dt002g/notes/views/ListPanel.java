package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.ListViewInterface;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;
import se.miun.dt002g.notes.models.Note;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * View class representing the panel that shows a list of notes
 * @author cabr2300
 */
public class ListPanel extends RoundedPanel implements ListViewInterface {

    private final JTextField searchField;
    private final JPanel noteListPanel;
    private final NoteControllerInterface noteController;
    private JLabel selectedLabel;
    private List<Note> allNotes;

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

        allNotes = new ArrayList<>();

        searchField = new JTextField();
        searchField.setFont(AppConfig.NOTE_CONTENT_FONT);
        searchField.setToolTipText(AppConfig.SEARCH_TOOLTIP);
        searchField.setMaximumSize(new Dimension((AppConfig.LIST_PANEL_WIDTH-10), searchField.getPreferredSize().height));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterNotes(searchField.getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterNotes(searchField.getText());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterNotes(searchField.getText());
            }
        });

        noteListPanel = new JPanel();
        noteListPanel.setLayout(new BoxLayout(noteListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(noteListPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(searchField);
        this.add(scrollPane);
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void showNotes(List<Note> notes) {
        allNotes = new ArrayList<>(notes);
        allNotes.sort(Comparator.comparing(Note::getModificationTime));
        filterNotes(searchField.getText());
    }

    /**
     * Creates a JLabel and sets a Note title as its text.
     * Adds a mouse click listener to the JLabel to be able to select a note.
     * Adds the JLabel to the note list panel.
     * @param note is a Note object.
     */
    private void addNoteToList(Note note) {
        JLabel label = new JLabel(note.getTitle());
        label.setOpaque(true);
        label.setBorder(BorderFactory.createBevelBorder(1));
        label.setMaximumSize(AppConfig.LIST_NOTE_SIZE);
        label.setFont(AppConfig.NOTE_CONTENT_FONT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (label == selectedLabel) {
                    return;
                }
                if (selectedLabel != null) {
                    selectedLabel.setBackground(getBackground());
                }
                selectedLabel = label;
                selectedLabel.setBackground(AppConfig.BACKGROUND_COLOR);
                noteController.onNoteSelected(note.getId());
            }
        });
        noteListPanel.add(label);
    }

    /**
     * Filter notes according to a search phrase.
     * @param query is a string entered by the user.
     */
    private void filterNotes(String query) {
        noteListPanel.removeAll();
        allNotes.stream()
                .filter(note -> note.getTitle().toLowerCase().contains(query.toLowerCase()))
                .forEach(this::addNoteToList);
        noteListPanel.revalidate();
        noteListPanel.repaint();
    }
}
