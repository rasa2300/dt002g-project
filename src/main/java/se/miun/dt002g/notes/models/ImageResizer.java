package se.miun.dt002g.notes.models;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * Model class that resizes buffered images.
 * @author cabr2300
 */
public class ImageResizer {

    /**
     * Resizes an image if it is larger than the target size.
     * @param image is a BufferedImage
     * @return the resized image if the original was larger, or else the unchanged original.
     */
    public BufferedImage resizeImage(BufferedImage image) {
        int originalSize = Integer.max(image.getHeight(), image.getWidth());
        int targetSize = 700;
        return targetSize < originalSize ? Scalr.resize(image, Scalr.Method.QUALITY, targetSize) : image;
    }
}
