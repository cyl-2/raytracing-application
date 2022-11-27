package raytracer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The ImagePanel Class
 *
 * Creates the image
 */
public class ImagePanel extends JPanel {
    private BufferedImage image;

    /**
     * Constructor
     *
     * @param filename
     * @throws IOException
     */
    public ImagePanel(String filename) throws IOException {
        image = ImageIO.read(new File(filename));
    }

    /**
     * Method to update the image as it is being rendered
     *
     * @param img   the updated image
     */
    public void updateImage(Image img) {
        image = (BufferedImage) img;
    }

    /**
     * Method to paint the image
     * 
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
        repaint();
    }

    /**
     * Save the scene/image as a png file
     *
     * @param img   the image to be saved
     */
    public void saveAsPNG(Image img)
    {
        try {
            if (ImageIO.write((BufferedImage) img, "png", new File("./rendered_image.png")))
            {
                Log.info("--> saved as PNG file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
