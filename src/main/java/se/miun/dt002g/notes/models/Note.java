package se.miun.dt002g.notes.models;

import se.miun.dt002g.notes.interfaces.NoteInterface;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 * Model class representing a note and implementing the NoteInterface for retrieving note details.
 * @author rasa2300
 * @author cabr2300
 */
public class Note implements NoteInterface {
    private long id;
    private String title;
    private String content;
    private LocalDateTime creationTime;
    private LocalDateTime modificationTime;
    private BufferedImage image;

    /**
     * Constructs a new Note object using provided data.
     * @param map A HashMap containing note details
     */
    public Note(HashMap<String, String> map) {
        this.updateNote(map);
        if (this.creationTime == null)
            this.creationTime = LocalDateTime.now();
        if (this.modificationTime == null)
            this.modificationTime = LocalDateTime.now();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getId() {
        return this.id;
    }

    /**
     * Sets the ID.
     * @param id The id
     */
    private void setId(long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title.
     * @param title The title
     */
    private void setTitle(String title) {
        this.title = title;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContent() {
        return this.content;
    }

    /**
     * Sets the description
     * @param description The description
     */
    private void setContent(String description) {
        this.content = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    /**
     * Sets the creation time.
     * @param creationTime The creation time
     */
    private void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getModificationTime() {
        return this.modificationTime;
    }

    /**
     * Sets the modification time.
     * @param modificationTime The modification time
     */
    private void setModificationTime(LocalDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    /**
     * "Touches" the note and updates the modification time to the current time.
     */
    public void touchNote() {
        this.modificationTime = LocalDateTime.now();
    }

    /**
     * Update a note with passed data.
     * @param map The data to update with
     * @throws IllegalArgumentException If no passed data
     * @throws NumberFormatException If invalid ID
     */
    public void updateNote(HashMap<String, String> map) {
        if (map.containsKey("id")) {
            try {
                this.setId(Long.parseLong(map.get("id")));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid Note ID");
            }
        }

        if (map.containsKey("title"))
            this.setTitle(map.get("title"));

        if (map.containsKey("content"))
            this.setContent(map.get("content"));

        if (map.containsKey("creationTime")) {
            try {
                this.setCreationTime(LocalDateTime.parse(map.get("creationTime")));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid creation time format");
            }
        }

        if (map.containsKey("modificationTime")) {
            try {
                this.setModificationTime(LocalDateTime.parse(map.get("modificationTime")));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid modification time format");
            }
        }
    }

    /**
     * Add an image to the note
     * @param image is a BufferedImage object.
     */
    public void addImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Get the note image.
     * @return a BufferedImage if exists, or null.
     */
    public BufferedImage getImage() {
        return image;
    }
}
