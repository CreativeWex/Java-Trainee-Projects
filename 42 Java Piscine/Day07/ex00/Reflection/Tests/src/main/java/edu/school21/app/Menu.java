package edu.school21.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;
import edu.school21.classes.Car.Car;
import edu.school21.classes.User.User;

import java.util.Arrays;

public  class Menu {
    static void main(boolean isCreatedObject) {
        System.out.println("================================================================");
        System.out.println("1 - display class information");
        System.out.println("2 - create an object of specified class");
        if (isCreatedObject) {
            System.out.println("3 - display information about the created class object");
            System.out.println("4 - call class methods");
        }
        System.out.println("0 - exit");
        System.out.println("================================================================");
    }

    static void classes(Scanner scanner, boolean isCreatedObject) {
        System.out.println("Classes\n1 - Car\n2 - User\n0 - Back");
        System.out.println("=======================");
        int classesOption = 42;
        String tmp;

        while (true) {
            if (scanner.hasNextInt()) {
                classesOption = scanner.nextInt();
                if (classesOption == 1) {
                    System.out.println("Class Car\n[fields]:");
                    Field[] fields = Car.class.getDeclaredFields();
                    Method[] methods = Car.class.getMethods();
                    String type;

                    for(int i = 0; i < fields.length; i++) {
                        System.out.println((fields[i].getType() + " " + fields[i].getName()).substring(16));
                    }
                    System.out.println("[methods]:");
                    for(int i = 0; i < methods.length - 8; i++) {
                        type = (methods[i].getAnnotatedReturnType()).toString();
                        if (type.contains("java.lang.")) {
                            type = type.substring(10);
                        }
                        tmp = Arrays.toString(methods[i].getParameterTypes());
                        tmp = tmp.substring(1, tmp.length() - 1);
                        if (tmp.contains("class java.lang.")) {
                            tmp = tmp.replace("class java.lang.", "");
                        }
                        System.out.println(type + " " + methods[i].getName() + "(" + tmp + ")");
                    }
                    System.out.println("=======================");
                }
                else if (classesOption == 2) {
                    System.out.println("Class User\n[fields]:");
                    Field[] fields = User.class.getDeclaredFields();
                    Method[] methods = User.class.getMethods();
                    String type;

                    for(int i = 0; i < fields.length; i++) {
                        type = fields[i].getType().toString();
                        if (type.contains("class java.lang.")) {
                            type = type.replace("class java.lang.", "");
                        }
                        System.out.println((type + " " + fields[i].getName()));
                    }
                    System.out.println("[methods]:");
                    for(int i = 0; i < methods.length - 8; i++) {
                        type = (methods[i].getAnnotatedReturnType()).toString();
                        if (type.contains("java.lang.")) {
                            type = type.substring(10);
                        }
                        tmp = Arrays.toString(methods[i].getParameterTypes());
                        tmp = tmp.substring(1, tmp.length() - 1);
                        if (tmp.contains("class java.lang.")) {
                            tmp = tmp.replace("class java.lang.", "");
                        }
                        System.out.println(type + " " + methods[i].getName() + "(" + tmp + ")");
                    }
                    System.out.println("=======================");
                }
                else if (classesOption == 0) {
                    main(isCreatedObject);
                    return;
                }
            }
            else {
                System.err.println("Wrong parameter");
                System.exit(-1);
            }
        }
    }

//    static void createObject(Scanner scanner, boolean isCreatedObject) {
//        System.out.println("Let's create an object");
//        System.out.println("Classes\n1 - Car\n2 - User\n0 - Back");
//        System.out.println("=======================");
//        int classesOption;
//
//        while (true) {
//            if (scanner.hasNextInt()) {
//                classesOption = scanner.nextInt();
//                if (classesOption == 1) {
//                    StringBuilder ClassConstructors = new StringBuilder();
//                    Constructor[] Constructors = Car.class.getConstructors();
//                    String separator = System.getProperty( "line.separator" );
//                    for (Constructor c: Constructors)
//                    {
////                        boolean isPublic = Modifier.isPublic(c.getModifiers());
//                        Class[] parameterTypes = c.getParameterTypes();
//                        for (Class pt : parameterTypes)
//                        {
//
//                        }
//                    }
//                }
//                else if (classesOption == 2) {
//
//                }
//                else if (classesOption == 0) {
//                    main(isCreatedObject);
//                    return;
//                }
//            }
//            else {
//                System.err.println("Wrong parameter");
//                System.exit(-1);
//            }
//        }
//    }
}
