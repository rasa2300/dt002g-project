package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;

import javax.swing.*;
import java.awt.*;

/**
 * View class representing the panel holding buttons for the user to interact with the GUI
 * @author cabr2300
 */
public class ButtonPanel extends RoundedPanel {

    NoteControllerInterface noteController;

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
        JButton createButton = new CustomButton(AppConfig.NEW_BUTTON_TEXT);
        createButton.addActionListener(e -> noteController.createNote());
        JButton editButton = new CustomButton(AppConfig.EDIT_BUTTON_TEXT);
        editButton.addActionListener(e -> noteController.editNote());
        JButton saveButton = new CustomButton(AppConfig.SAVE_BUTTON_TEXT);
        saveButton.addActionListener(e -> noteController.saveNote());
        JButton deleteButton = new CustomButton(AppConfig.DELETE_BUTTON_TEXT);
        deleteButton.addActionListener(e -> noteController.deleteNote());
        this.add(notesLabel);
        this.add(createButton);
        this.add(editButton);
        this.add(saveButton);
        this.add(deleteButton);
        //TODO toggle visibility of the buttons depending on what actions are currently possible
    }
}
