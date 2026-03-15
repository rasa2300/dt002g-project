package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;
import se.miun.dt002g.notes.controllers.NoteController;
import se.miun.dt002g.notes.interfaces.NoteControllerInterface;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Class representing the main application window.
 * @author cabr2300
 */
public class MainFrame extends JFrame {

    /**
     * Class constructor setting the layout of the three panels making up the GUI.
     * Instantiates the NoteController and the Panel classes, passing the controller interface to them.
     * Sets the controller's note and list views.
     */
    public MainFrame() {
        this.setTitle(AppConfig.TITLE);
        this.setSize(AppConfig.WINDOW_WIDTH,AppConfig.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(AppConfig.BACKGROUND_COLOR);

        NoteControllerInterface noteController = new NoteController();
        ButtonPanel buttonPanel = new ButtonPanel(noteController);
        ListPanel listPanel = new ListPanel(noteController);
        NotePanel notePanel = new NotePanel();

        noteController.setNoteView(notePanel);
        noteController.setListView(listPanel);
        noteController.setButtonView(buttonPanel);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(AppConfig.GRID_BAG_INSETS, AppConfig.GRID_BAG_INSETS, AppConfig.GRID_BAG_INSETS, AppConfig.GRID_BAG_INSETS);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridwidth = 2;
        this.add(buttonPanel, gbc);

        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        this.add(listPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        this.add(notePanel, gbc);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
