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
    public static final Dimension LIST_NOTE_SIZE = new Dimension(Integer.MAX_VALUE, 50);
    public static final Dimension DROP_AREA_SIZE = new Dimension(Integer.MAX_VALUE, 50);
    public static final Dimension NOTE_TITLE_SIZE = new Dimension(20, 20);
    public static final int IMAGE_SIZE = 500;

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
    public static final String ALL_NOTES = "All Notes";
    public static final String NEW_BUTTON_TEXT = "new note";
    public static final String EDIT_BUTTON_TEXT = "edit note";
    public static final String SAVE_BUTTON_TEXT = "save note";
    public static final String DELETE_BUTTON_TEXT = "delete note";
    public static final String CONFIRM_DELETE = "Are you sure?";
    public static final String DROP_AREA_INITIAL = "drop image here";
    public static final String DROP_AREA_PROCESSING = "processing image...";
    public static final String DROP_AREA_DONE = "image added";
    public static final String IMAGE_BUTTON_TEXT = "IMG";
    public static final String IMAGE_NAME_PLACEHOLDER = "Image";
    public static final String DELETE_IMAGE = "delete image";

    /**
     * Tooltips
     */
    public static final String SEARCH_TOOLTIP = "Search all notes";
    public static final String TITLE_TOOLTIP = "Enter the title of your note";
    public static final String CONTENT_TOOLTIP = "Write your note here";
    public static final String DROP_AREA_TOOLTIP = "Drop an image here to attach it to your note";
    public static final String IMAGE_BUTTON_TOOLTIP = "Display the note image";
    public static final String NEW_NOTE_TOOLTIP = "Create a new note";
    public static final String SAVE_NOTE_TOOLTIP = "Save the current note";
    public static final String DELETE_NOTE_TOOLTIP = "Delete the displayed note";
    public static final String EDIT_NOTE_TOOLTIP = "Edit the currently displayed note";
    public static final String DELETE_IMAGE_BUTTON_TOOLTIP = "Delete the current image attached to the note";

    /**
     * Fonts
     */
    public static final Font NOTE_TITLE_FONT = new Font("Arial", Font.BOLD, 18);
    public static final Font NOTE_CONTENT_FONT = new Font("Arial", Font.PLAIN, 15);
    public static final Font MENU_FONT = new Font("Arial", Font.PLAIN, 20);

    /**
     * File and folder names
     */
    public static final String DB_FILE_NAME = "notes.db";
    public static final String IMAGE_FOLDER_NAME = "images";

    /**
     * Error messages
     */
    public static final String TITLE_CANNOT_LOAD_NOTES = "ERROR LOADING NOTES";
    public static final String CONTENT_CANNOT_LOAD_NOTES = "The saved notes could not be retrieved from the database. You may delete this placeholder.";

}
