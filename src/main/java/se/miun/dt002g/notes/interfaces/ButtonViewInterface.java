package se.miun.dt002g.notes.interfaces;

/**
 * Interface for the ButtonPanel class
 * @author cabr2300
 */
public interface ButtonViewInterface {

    /**
     * Informs about whether a note is selected.
     * @param isSelected is true if note is selected, otherwise false
     */
    void noteSelected(boolean isSelected);

    /**
     * Informs about whether a note is being edited.
     * @param isEditing is true if note is being edited, otherwise false.
     */
    void editingNote(boolean isEditing);

    /**
     * Show a delete confirmation dialog.
     * @return whether the user selected yes or no
     */
    boolean showDeleteConfirmation();
}
