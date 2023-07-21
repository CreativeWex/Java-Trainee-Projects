package edu.school21.printer.logic;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Logic {
    private final BufferedImage image;
    private final String blackColorSymbol;
    private final String whiteColorSymbol;


    public Logic(BufferedImage image, String blackColorSymbol, String whiteColorSymbol) {
        this.blackColorSymbol = blackColorSymbol;
        this.whiteColorSymbol = whiteColorSymbol;
        this.image = image;
    }

    public void setCharsFromImage() {
        ColoredPrinter coloredPrinter = new ColoredPrinter.Builder(1, false).build();
        for(int i = 0; i < image.getHeight(); i++) {
            for(int j = 0; j < image.getWidth(); j++) {
                int color = image.getRGB(j, i);
                if(color == Color.BLACK.getRGB()) {
                    coloredPrinter.setBackgroundColor(Ansi.BColor.valueOf(blackColorSymbol));
                    coloredPrinter.print(" ");
                }
                if(color == Color.WHITE.getRGB()) {
                    coloredPrinter.setBackgroundColor(Ansi.BColor.valueOf(whiteColorSymbol));
                    coloredPrinter.print(" ");
                }
            }
            System.out.println();
        }
    }
}