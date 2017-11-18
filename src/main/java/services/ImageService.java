package services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageService {
    public static ImageIcon getImageIcon(String filename) throws IOException {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename);
        ImageIcon icon= new ImageIcon(ImageIO.read(is));
        return icon;
    }
}
