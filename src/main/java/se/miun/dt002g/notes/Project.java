package se.miun.dt002g.notes;

import se.miun.dt002g.notes.views.MainFrame;

import javax.swing.*;

/**
 * The main starting point for Project.
 */
public final class Project {
    private Project() { throw new IllegalStateException("Utility class"); }

    /**
     * Main program entrypoint.
     * @param args command arguments.
     */
    public static void main(final String... args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
