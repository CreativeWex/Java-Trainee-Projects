package edu.school21.app;

import java.util.Scanner;

public class Program {

    static boolean isCreatedObject;

    public static void main(String[] args) {
        isCreatedObject = false;
        Scanner scanner = new Scanner(System.in);
        int option = 42;

        Menu.main(isCreatedObject);
        while (option != 0) {
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                if (option == 1) {
                    Menu.classes(scanner, isCreatedObject);
                }
                if (option == 2) {
                    isCreatedObject = true;
//                    Menu.createObject(scanner, isCreatedObject);
                }
            }
            else {
                System.err.println("Wrong parameter");
                System.exit(-1);
            }
        }
    }
}
