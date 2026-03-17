package se.miun.dt002g.notes.models;

import se.miun.dt002g.notes.config.AppConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Model class that saves, loads and deletes images.
 * @author cabr2300
 */
public class ImageHandler {

    private final String imageFolderPath;

    /**
     * Class constructor. Defines the relative path to the image folder and creates it if necessary.
     */
    public ImageHandler() {
        String jarLocation;
        try {
            jarLocation = new File(ImageHandler.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        this.imageFolderPath = Paths.get(jarLocation, AppConfig.IMAGE_FOLDER_NAME).toString();
        // create image folder if it doesn't exist already
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
    }

    /**
     * Gets the path to an image file.
     * @param id is the unique identifier of the note the image is associated with, also used as the file name.
     * @return the path including file name.
     */
    private String getImagePath(Long id) {
        return Paths.get(imageFolderPath, id + ".jpg").toString();
    }

    /**
     * Saves an image to the image folder.
     * @param image is a BufferedImage.
     * @param noteId is the unique id of the note that the image belongs to.
     * @return whether the image was saved successfully or not.
     */
    public boolean saveImage(BufferedImage image, long noteId) {
        try {
            ImageIO.write(image, "jpg", new File(getImagePath(noteId)));
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    /**
     * Gets a specific image.
     * @param noteId is the unique identifier of a note, as well as the image file name.
     * @return the image as a BufferedImage object.
     */
    public BufferedImage getImage(long noteId) {
        System.out.println(noteId);
        try {
            return ImageIO.read(new File(getImagePath(noteId)));
        } catch(IOException e) {
            return null;
        }
    }

    /**
     * Deletes an image saved in the image folder.
     * @param noteId is the unique identifier of a note, as well as the image file name.
     * @return whether the image was successfully deleted or not.
     */
    public boolean deleteImage(long noteId) {
        return new File(getImagePath(noteId)).delete();
    }
}
