package se.miun.dt002g.notes.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

public class ImageHandlerTest {

    ImageHandler handler;

    @BeforeEach
    void setup() {
        handler = new ImageHandler();
    }

    @AfterEach
    void tearDown() {
        handler = null;
    }

    /*
    @Test
    void testImagePath() {
        String imagePath = handler.getImagePath((long) 999);
        assertEquals(handler.getImageFolderPath() + "\\999.jpg", imagePath);
        System.out.println(imagePath);
    }
    */

    @Test
    void testSaveImage() {
        BufferedImage image = new BufferedImage(500, 500, 1);
        assertTrue(handler.saveImage(image, 888));
    }

    @Test
    void testGetImage() {
        BufferedImage image = new BufferedImage(500, 500, 1);
        assertTrue(handler.saveImage(image, 888));
        assertNotNull(handler.getImage(888));
    }

    @Test
    void testDeleteImage() {
        BufferedImage image = new BufferedImage(500, 500, 1);
        assertTrue(handler.saveImage(image, 888));
        assertNotNull(handler.getImage(888));
        assertTrue(handler.deleteImage(888));
        assertNull(handler.getImage(888));
    }
}
