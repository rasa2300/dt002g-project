package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;

import javax.swing.JButton;

/**
 * A custom button design.
 * Subclass of JButton.
 * @author cabr2300
 */
public class CustomButton extends JButton {

    /**
     * Class constructor, setting the font and colors
     * @param buttonText is the text to be displayed on the button.
     */
    public CustomButton(String buttonText) {
        super(buttonText);
        this.setFont(AppConfig.MENU_FONT);
        this.setBackground(AppConfig.BUTTON_BACKGROUND);
        this.setForeground(AppConfig.BUTTON_FOREGROUND);
        this.setFocusPainted(false);
    }
}
