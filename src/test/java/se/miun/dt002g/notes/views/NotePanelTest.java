package se.miun.dt002g.notes.views;

import org.junit.jupiter.api.*;
import se.miun.dt002g.notes.config.AppConfig;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.*;
import java.util.concurrent.atomic.AtomicLong;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

public class NotePanelTest {

    private NotePanel panel;

    @BeforeEach
    void setUp() throws Exception {
        // Must create the panel on the EDT
        SwingUtilities.invokeAndWait(() -> panel = new NotePanel());
    }

    @AfterEach
    void tearDown() throws Exception {
        SwingUtilities.invokeAndWait(() -> panel = null);
    }

    /**
     * Helper to inject a BufferedImage into the private image field
     * since there is no public setter
     */
    private void setImage(BufferedImage image) throws Exception {
        Field imageField = NotePanel.class.getDeclaredField("image");
        imageField.setAccessible(true);
        imageField.set(panel, image);
    }

    /**
     * Helper to invoke the private displayImage() method
     */
    private void invokeDisplayImage() throws Exception {
        Method method = NotePanel.class.getDeclaredMethod("displayImage");
        method.setAccessible(true);
        method.invoke(panel);
    }

    @Test
    void testDisplayImageSpeed_small() throws Exception {
        setImage(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));

        AtomicLong duration = new AtomicLong();

        SwingUtilities.invokeAndWait(() -> {
            try {
                long start = System.nanoTime();
                invokeDisplayImage();
                duration.set(System.nanoTime() - start);
            } catch (Exception e) {
                fail("displayImage() threw an exception: " + e.getMessage());
            }
        });

        long ms = duration.get() / 1_000_000;
        System.out.printf("Small image (100x100): %d ms%n", ms);
        assertTrue(ms < 500, "Small image display took too long: " + ms + "ms");
    }

    @Test
    void testDisplayImageSpeed_large() throws Exception {
        setImage(new BufferedImage(3000, 3000, BufferedImage.TYPE_INT_RGB));

        AtomicLong duration = new AtomicLong();

        SwingUtilities.invokeAndWait(() -> {
            try {
                long start = System.nanoTime();
                invokeDisplayImage();
                duration.set(System.nanoTime() - start);
            } catch (Exception e) {
                fail("displayImage() threw an exception: " + e.getMessage());
            }
        });

        long ms = duration.get() / 1_000_000;
        System.out.printf("Large image (3000x3000): %d ms%n", ms);
        assertTrue(ms < 2000, "Large image display took too long: " + ms + "ms");
    }

    @Test
    void testDisplayImageSpeed_multipleRuns() throws Exception {
        setImage(new BufferedImage(AppConfig.IMAGE_SIZE, AppConfig.IMAGE_SIZE, BufferedImage.TYPE_INT_RGB));

        int runs = 5;
        long total = 0;

        for (int i = 0; i < runs; i++) {
            AtomicLong duration = new AtomicLong();
            SwingUtilities.invokeAndWait(() -> {
                try {
                    long start = System.nanoTime();
                    invokeDisplayImage();
                    duration.set(System.nanoTime() - start);
                } catch (Exception e) {
                    fail("displayImage() threw an exception: " + e.getMessage());
                }
            });
            total += duration.get();
        }

        long avgMs = (total / runs) / 1_000_000;
        System.out.printf("Average over %d runs (500x500): %d ms%n", runs, avgMs);
        assertTrue(avgMs < 500, "Average display time too high: " + avgMs + "ms");
    }

    @Test
    void testButtonClickToFrameVisibleResponseTime() throws Exception {
        setImage(new BufferedImage(AppConfig.IMAGE_SIZE, AppConfig.IMAGE_SIZE, BufferedImage.TYPE_INT_RGB));

        // Use a CountDownLatch to block the test thread
        // until the frame signals it is visible
        CountDownLatch frameLatch = new CountDownLatch(1);
        AtomicLong startTime = new AtomicLong();
        AtomicLong endTime = new AtomicLong();

        // Intercept frame creation by overriding displayImage() behavior
        // We attach a global AWTEventListener to catch WindowOpened events
        AWTEventListener windowListener = event -> {
            if (event instanceof WindowEvent) {
                WindowEvent we = (WindowEvent) event;
                if (we.getID() == WindowEvent.WINDOW_OPENED) {
                    // Frame is now visible — record the time
                    endTime.set(System.nanoTime());
                    frameLatch.countDown();
                }
            }
        };

        Toolkit.getDefaultToolkit().addAWTEventListener(
                windowListener,
                AWTEvent.WINDOW_EVENT_MASK
        );

        try {
            // Simulate the button click on the EDT
            SwingUtilities.invokeAndWait(() -> {
                startTime.set(System.nanoTime());

                // Find and click the display button via reflection
                try {
                    Field buttonField = NotePanel.class.getDeclaredField("displayButton");
                    buttonField.setAccessible(true);
                    JButton displayButton = (JButton) buttonField.get(panel);
                    displayButton.doClick();
                } catch (Exception e) {
                    fail("Could not click display button: " + e.getMessage());
                }
            });

            // Wait up to 5 seconds for the frame to open
            boolean opened = frameLatch.await(5, TimeUnit.SECONDS);
            assertTrue(opened, "Frame did not open within 5 seconds");

            long responseMs = (endTime.get() - startTime.get()) / 1_000_000;
            System.out.printf("Button click to frame visible: %d ms%n", responseMs);
            assertTrue(responseMs < 100, "Response time too slow: " + responseMs + "ms");

        } finally {
            // Always clean up the global listener
            Toolkit.getDefaultToolkit().removeAWTEventListener(windowListener);

            // Close any opened frames
            SwingUtilities.invokeAndWait(() -> {
                for (Window window : Window.getWindows()) {
                    if (window instanceof JFrame && window != SwingUtilities.getWindowAncestor(panel)) {
                        window.dispose();
                    }
                }
            });
        }
    }
}
