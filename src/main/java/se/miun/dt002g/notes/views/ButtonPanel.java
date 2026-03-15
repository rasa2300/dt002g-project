package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.ButtonViewInterface;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;

import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * View class representing the panel holding buttons for the user to interact with the GUI
 * @author cabr2300
 */
public class ButtonPanel extends RoundedPanel implements ButtonViewInterface {

    NoteControllerInterface noteController;
    JButton editButton;
    JButton saveButton;
    JButton deleteButton;

    /**
     * Class constructor setting up the structure and elements.
     * @param noteController is the interface of the NoteController
     */
    public ButtonPanel(NoteControllerInterface noteController) {
        this.noteController = noteController;
        this.setPreferredSize(new Dimension(0, AppConfig.BUTTON_PANEL_HEIGHT));
        this.setLayout(new GridLayout(1,4,10,0));
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel notesLabel = new JLabel(AppConfig.ALL_NOTES);
        notesLabel.setFont(AppConfig.MENU_FONT);

        JButton createButton = setUpButton(noteController::createNote, AppConfig.NEW_BUTTON_TEXT);
        createButton.setToolTipText(AppConfig.NEW_NOTE_TOOLTIP);

        editButton = setUpButton(noteController::editNote, AppConfig.EDIT_BUTTON_TEXT);
        editButton.setToolTipText(AppConfig.EDIT_NOTE_TOOLTIP);

        saveButton = setUpButton(noteController::saveNote, AppConfig.SAVE_BUTTON_TEXT);
        saveButton.setToolTipText(AppConfig.SAVE_NOTE_TOOLTIP);

        deleteButton = setUpButton(noteController::deleteNote, AppConfig.DELETE_BUTTON_TEXT);
        deleteButton.setToolTipText(AppConfig.DELETE_NOTE_TOOLTIP);

        this.add(notesLabel);
        this.add(createButton);
        this.add(editButton);
        this.add(saveButton);
        this.add(deleteButton);

        noteSelected(false);
    }

    /**
     * Create a button element and set up its action listener
     * @param function is the function to call when button clicked
     * @param buttonText is the text to display on  the button
     * @return the button element.
     */
    private JButton setUpButton(Runnable function, String buttonText) {
        JButton button = new CustomButton(buttonText);
        button.addActionListener(e -> function.run());
        return button;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void noteSelected(boolean isSelected) {
        deleteButton.setVisible(isSelected);
        if(!isSelected) {
            editButton.setVisible(false);
            saveButton.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editingNote(boolean isEditing) {
        editButton.setVisible(!isEditing);
        saveButton.setVisible(isEditing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean showDeleteConfirmation() {
        int result = JOptionPane.showConfirmDialog(
                this,
                AppConfig.DELETE_BUTTON_TEXT,
                AppConfig.CONFIRM_DELETE,
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }
}
