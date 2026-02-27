package se.miun.dt002g.notes.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Note model, testing construction and updating of note details.
 * @author rasa2300
 */
public class NoteTest {

    /**
     * Tests that setting fields during construction should work.
     */
    @Test
    public void shouldCreateNote() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "42");
        map.put("title", "Test title");
        map.put("content", "Some content");
        String creation = LocalDateTime.now().withNano(0).toString();
        String modification = LocalDateTime.now().withNano(0).toString();
        map.put("creationTime", creation);
        map.put("modificationTime", modification);

        Note note = new Note(map);

        assertEquals(42L, note.getId());
        assertEquals("Test title", note.getTitle());
        assertEquals("Some content", note.getContent());
        assertEquals(LocalDateTime.parse(creation), note.getCreationTime());
        assertEquals(LocalDateTime.parse(modification), note.getModificationTime());
    }

    /**
     * Test being able to update existing fields as well as touching the note,
     * updating its modification date
     */
    @Test
    public void shouldUpdateNote() throws InterruptedException {
        HashMap<String, String> empty = new HashMap<>();
        Note note = new Note(empty);

        // initial modification time
        LocalDateTime beforeTouch = note.getModificationTime();
        Thread.sleep(10);
        note.touchNote();
        assertTrue(note.getModificationTime().isAfter(beforeTouch));

        // update partial fields
        HashMap<String, String> update = new HashMap<>();
        update.put("title", "New title");
        update.put("content", "Updated content");
        update.put("id", "123");
        note.updateNote(update);

        assertEquals(123L, note.getId());
        assertEquals("New title", note.getTitle());
        assertEquals("Updated content", note.getContent());
    }

    /**
     * Should throw an exception on a non-integer ID value
     */
    @Test
    public void shouldThrowInvalidId() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "not-a-number");

        assertThrows(IllegalArgumentException.class, () -> new Note(map));
    }

    /**
     * Should throw an exception on invalid time format
     */
    @Test
    public void shouldThrowInvalidCreationTime() {
        HashMap<String, String> map = new HashMap<>();
        map.put("creationTime", "bad-time-format");

        assertThrows(IllegalArgumentException.class, () -> new Note(map));
    }

    /**
     * Should throw an exception on invalid time format
     */
    @Test
    public void shouldThrowInvalidModificationTime() {
        HashMap<String, String> map = new HashMap<>();
        map.put("modificationTime", "bad-time-format");

        assertThrows(IllegalArgumentException.class, () -> new Note(map));
    }

}
