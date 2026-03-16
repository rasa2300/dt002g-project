package se.miun.dt002g.notes.views;

import javax.imageio.ImageIO;
import javax.swing.TransferHandler;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * Class that accepts an image transfer and delivers it with a callback.
 * @author cabr2300
 */
public class ImageDropHandler extends TransferHandler {

    private final Consumer<BufferedImage> onImageDropped;

    /**
     * Class constructor that assigns the Consumer of the
     * @param onImageDropped is a callback function that accepts a BufferedImage and returns nothing.
     */
    public ImageDropHandler(Consumer<BufferedImage> onImageDropped) {
        this.onImageDropped = onImageDropped;
    }

    /**
     * Determines the accepted elements that can be transferred.
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return true if the element is accepted, otherwise false.
     */
    @Override
    public boolean canImport(TransferSupport support) {
        // accept image objects or image files
        return support.isDataFlavorSupported(DataFlavor.imageFlavor)
               || support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    /**
     * Tries to convert the element into a BufferedImage.
     * If successful, accepts it.
     * @param support the object containing the details of
     *        the transfer, not <code>null</code>.
     * @return true if the image is accepted, otherwise false.
     */
    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;
        // copy instead of move
        support.setDropAction(COPY);
        try {
            Transferable t = support.getTransferable();
            BufferedImage image;
            // File drop
            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                java.util.List<java.io.File> files =
                        (java.util.List<java.io.File>) t.getTransferData(DataFlavor.javaFileListFlavor);
                if (files.isEmpty()) return false; // method triggered without anything being dropped
                // Get only first image if dropping multiple
                java.io.File file = files.get(0);
                // check that dropped item is file not directory
                if (!file.isFile()) return false; // dropped item not a file
                image = ImageIO.read(file);
                if (image == null) {
                    return false;  // not a valid image file
                }
                onImageDropped.accept(image);
            }
            // Direct image flavor
            if (t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                image = (BufferedImage) t.getTransferData(DataFlavor.imageFlavor);
                onImageDropped.accept(image);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
