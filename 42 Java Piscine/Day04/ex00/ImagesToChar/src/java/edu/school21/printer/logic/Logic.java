package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Logic {
    public static int[][] seeBMPImage(String Filename, char charFilled, char charEmpty) throws IOException {
        BufferedImage image = ImageIO.read(new FileInputStream(Filename));

        int[][] arr = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);
                if (color == Color.BLACK.getRGB()) {
                    arr[x][y] = charFilled;
                } else if (color == Color.WHITE.getRGB()) {
                    arr[x][y] = charEmpty;
                }
            }
        }
        return arr;
    }
}
