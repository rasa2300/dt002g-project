package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.NoteViewInterface;
import se.miun.dt002g.notes.models.Note;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * View class representing the panel displaying a single note
 * @author cabr2300
 */
public class NotePanel extends RoundedPanel implements NoteViewInterface {

    private final JTextField titleField;
    private final JTextArea contentArea;
    private final JScrollPane scrollPane;
    private final JLabel dropArea;
    private BufferedImage image;
    private Note note;
    private final JButton displayButton;

    /**
     * Class constructor setting up the structure.
     */
    public NotePanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        titleField = new JTextField("");
        titleField.setFont(AppConfig.NOTE_TITLE_FONT);
        titleField.setBackground(AppConfig.PANEL_COLOR);
        titleField.setToolTipText(AppConfig.TITLE_TOOLTIP);

        displayButton = setupDisplayButton();

        dropArea = new JLabel();
        setUpDropArea(dropArea);
        dropArea.setToolTipText(AppConfig.DROP_AREA_TOOLTIP);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleField, BorderLayout.NORTH);
        topPanel.add(dropArea, BorderLayout.CENTER);
        topPanel.add(displayButton, BorderLayout.WEST);

        contentArea = new JTextArea("");
        contentArea.setFont(AppConfig.NOTE_CONTENT_FONT);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setToolTipText(AppConfig.CONTENT_TOOLTIP);

        scrollPane = new JScrollPane(contentArea);
        scrollPane.setBackground(AppConfig.PANEL_COLOR);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        toggleVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayNote(Note note) {
        this.note = note;
        this.image = note.getImage();
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
        note = null;
        image = null;
        toggleVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Note getNote() {
        HashMap<String, String> data = new HashMap<>();
        data.put("title", titleField.getText());
        data.put("content", contentArea.getText());
        if(note == null) {
            note = new Note(data);
        } else {
            note.updateNote(data);
        }
        return note;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleEditable(boolean isEditable) {
        titleField.setEditable(isEditable);
        contentArea.setEditable(isEditable);
        dropArea.setVisible(isEditable);
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
        dropArea.setVisible(isVisible);
    }

    /**
     * Set up the image drop area so that image dropped there are saved as the class-level image member
     * @param dropArea is a JLabel used to drop images onto
     */
    private void setUpDropArea(JLabel dropArea) {
        dropArea.setText(AppConfig.DROP_AREA_INITIAL);
        dropArea.setHorizontalAlignment(SwingConstants.CENTER);
        dropArea.setFont(AppConfig.NOTE_CONTENT_FONT);
        dropArea.setForeground(AppConfig.BUTTON_BACKGROUND);
        dropArea.setBorder(BorderFactory.createLineBorder(AppConfig.BUTTON_BACKGROUND));
        dropArea.setPreferredSize(AppConfig.DROP_AREA_SIZE);
        dropArea.setTransferHandler(new ImageDropHandler(image -> {
            this.image = image;
            dropArea.setText(AppConfig.DROP_AREA_PROCESSING);
            System.out.println(this.image.toString());
            Timer timer = new Timer(2000, e -> {
                dropArea.setText(AppConfig.DROP_AREA_DONE);
            });
            displayButton.setVisible(true);
            timer.setRepeats(false);  // fire once then stop
            timer.start();
        }));
    }

    /**
     * Creates the button that displays the image attached to the note
     * @return the button
     */
    private JButton setupDisplayButton() {
        JButton button = new CustomButton(AppConfig.IMAGE_BUTTON_TEXT);
        button.setToolTipText(AppConfig.IMAGE_BUTTON_TOOLTIP);
        button.addActionListener(e -> {
            if(image != null) {
                displayImage();
            }
        });
        button.setVisible(false);
        return button;
    }

    /**
     * Open a new JFrame displaying the image that was added to the note.
     */
    private void displayImage() {
        String title;
        if(note == null || note.getTitle() == null) {
            title = AppConfig.IMAGE_NAME_PLACEHOLDER;
        } else {
            title = note.getTitle();
        }
        JFrame frame = new JFrame(title);
        JLabel imageLabel = new JLabel(new ImageIcon(this.image));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(imageLabel);
        frame.pack();
        frame.setLocation(this.getX() + this.getWidth(), this.getY());
        frame.setVisible(true);
    }
}
