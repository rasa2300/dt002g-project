package se.miun.dt002g.notes.config;

import java.awt.*;

/**
 * App configuration class
 * Only used for storing and retrieving constants
 * @author cabr2300
 */
public final class AppConfig {

    /**
     * Prohibit instantiation
     */
    private AppConfig() { throw new IllegalStateException("Utility class"); }

    /**
     * GUI dimensions
     */
    public static final int WINDOW_WIDTH = 890;
    public static final int WINDOW_HEIGHT = 740;
    public static final int BUTTON_PANEL_HEIGHT = 50;
    public static final int LIST_PANEL_WIDTH = 300;
    public static final int GRID_BAG_INSETS = 2;
    public static final int ROUNDED_CORNER_RADIUS = 40;

    /**
     * GUI colors
     */
    public static final Color BACKGROUND_COLOR = Color.lightGray;
    public static final Color PANEL_COLOR = Color.WHITE;
    public static final Color BUTTON_BACKGROUND = Color.GRAY;
    public static final Color BUTTON_FOREGROUND = Color.WHITE;

    /**
     * GUI texts
     */
    public static final String TITLE = "ImageNote";
    public static final String SEARCH_TOOLTIP = "Search all notes";
    public static final String ALL_NOTES = "All Notes";
    public static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 20);
    public static final String NEW_BUTTON_TEXT = "new note";
    public static final String EDIT_BUTTON_TEXT = "edit note";
    public static final String SAVE_BUTTON_TEXT = "save note";
    public static final String DELETE_BUTTON_TEXT = "delete note";

}
