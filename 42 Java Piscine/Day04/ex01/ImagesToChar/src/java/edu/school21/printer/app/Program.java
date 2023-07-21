package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args[0].length() != 1 || args[1].length() != 1) {
            System.out.println("Error: wrong number of args");
            System.exit(-1);
        }
        char charFilled = args[1].charAt(0);
        char charEmpty = args[0].charAt(0);

        int[][]arr = Logic.seeBMPImage(charFilled, charEmpty);
        for (int x = 0; x < arr.length; x++) {
            for (int y = 0; y < arr[x].length; y++) {
                System.out.print((char)arr[y][x]);
            }
            System.out.println();
        }
    }
}
