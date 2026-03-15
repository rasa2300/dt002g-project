package se.miun.dt002g.notes.interfaces;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * Interface for retrieving note details.
 * @author rasa2300
 * @author cabr2300
 */
public interface NoteInterface {

    /**
     * Gets the ID.
     * @return the ID
     */
    long getId();

    /**
     * Gets the title.
     * @return The title
     */
    String getTitle();

    /**
     * Gets the content
     * @return The content
     */
    String getContent();

    /**
     * Gets the creation time
     * @return The creation time
     */
    LocalDateTime getCreationTime();

    /**
     * Gets the modification time.
     * @return The modification time
     */
    LocalDateTime getModificationTime();

    /**
     * Gets the image attached to the note, if it exists.
     * @return a BufferedImage
     */
    BufferedImage getImage();

}
