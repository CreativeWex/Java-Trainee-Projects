import java.util.Scanner;

public class Program {
    private static final int MAX_STUDENTS_NUMBER = 10;
    private static final int MAX_NAME_LENGTH = 10;
    private static final int MAX_LESSONS_PER_WEEK = 10;

    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] students = new String[MAX_STUDENTS_NUMBER];
    private static final int[] lessonsTime = new int[MAX_LESSONS_PER_WEEK];
    private static final String[] lessonsName = new String[MAX_LESSONS_PER_WEEK];

    public static void main(String[] args) {
        initAndValidateStudents();
        displayStudents();
        initAndValidateLessons();
        displayLessons();
        int attendanceRecords = initAttendanceAndCountRecords();
    }

    private static void initAndValidateStudents() {
        System.out.println("Enter students (max = 10):");
        for (int i = 0; i < MAX_STUDENTS_NUMBER; i++) {
            System.out.print("[" + (i + 1) + "]: ");
            String input = scanner.nextLine();
            if (input.equals(".")) {
                break;
            }
            students[i] = input;
            char[] name = students[i].toCharArray();
            for (char c : name) {
                if (c == ' ' || name.length > MAX_NAME_LENGTH) {
                    System.err.println("Illegal argument");
                    System.exit(-1);
                }
            }
        }
    }

    private static void initAndValidateLessons() {
        System.out.println("Enter lessons: <time (1pm - 6pm)> <name>");
        for (int i = 0; i < MAX_LESSONS_PER_WEEK; i++) {
            System.out.print("[" + (i + 1) + "]: ");
            if (!scanner.hasNextInt()) {
                if (scanner.next().equals(".")) {
                    break;
                }
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            int time = scanner.nextInt();
            if (time < 1 || time > 6) {
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            String name = scanner.next();
            if (name.equals(".")) {
                break;
            }
            lessonsTime[i] = time;
            lessonsName[i] = name;
        }
    }

    private static int initAttendanceAndCountRecords() {
        int recordsNumber = 0;
        System.out.println("Enter attendance <student> <lesson> <date> <HERE/NOT_HERE>: ");
        while (!scanner.next().equals(".")) {
            String student = scanner.next();
            String lesson = scanner.next();
            if (isStudentNotExists(student) || isLessonNotExists(lesson) || !scanner.hasNextInt()) {
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            int date = scanner.nextInt();
            String presence = scanner.next();
            if (date < 1 || date > 31 || !(presence.equals("HERE") || presence.equals("HERE/NOT_HERE"))) {
                System.err.println("Illegal argument");
                System.exit(-1);
            }
            recordsNumber++;
        }
        return recordsNumber;
    }

    public static Boolean isStudentNotExists(String name) {
        for(String student: students) {
            if(name.equals(student))
                return false;
        }
        return true;
    }

    public static Boolean isLessonNotExists(String name) {
        for(String lesson: lessonsName) {
            if(name.equals(lesson))
                return false;
        }
        return true;
    }

    private static void displayStudents() {
        System.out.print("Students: ");
        for (String student : students) {
            if (student != null) {
                System.out.print(student + " ");
            }
        }
        System.out.println();
    }

    private static void displayLessons() {
        System.out.print("Lessons: ");
        for (int i = 0; i < MAX_LESSONS_PER_WEEK; i++) {
            if (lessonsName[i] != null && lessonsTime[i] > 0) {
                System.out.print(lessonsTime[i] + " " + lessonsName[i] + "; ");
            }
        }
        System.out.println();
    }
}


