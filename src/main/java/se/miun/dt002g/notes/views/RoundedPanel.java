package se.miun.dt002g.notes.views;

import se.miun.dt002g.notes.config.AppConfig;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

/**
 * Custom JPanel design with rounded corners.
 * @author cabr2300
 */
public class RoundedPanel extends JPanel {

    private final int radius = AppConfig.ROUNDED_CORNER_RADIUS;
    private final Color backgroundColor = AppConfig.PANEL_COLOR;

    /**
     * Class constructor, sets the painted pixels to not opaque
     */
    public RoundedPanel() {
        this.setOpaque(false);
    }

    /**
     * Creates the rounded corners.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Enable anti-aliasing for smooth corners
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill rounded rectangle
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();

        // Paint child components
        super.paintComponent(g);
    }

    /**
     * Sets the clickable area of the panel to match its visual shape.
     * @param x   the <i>x</i> coordinate of the point
     * @param y   the <i>y</i> coordinate of the point
     * @return whether the coordinates are within the clickable area.
     */
    @Override
    public boolean contains(int x, int y) {
        // Only allow mouse events within the rounded rectangle
        Shape shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        return shape.contains(x, y);
    }
}
