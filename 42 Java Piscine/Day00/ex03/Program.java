import java.util.Scanner;

public class Program {
    private static final String WEEK_TITLE = "Week ";
    private static final String EOF = "42";
    static int WEEKS = 18;

    private static void display(int length) {
        while (length > 0) {
            System.out.print("=");
            --length;
        }
        System.out.println(">");
    }
    private static long power(long base, long power) {
        long result = 1;
        while (power > 0) {
            result *= base;
            --power;
        }
        return result;
    }

    private static void createStorage(int grade, int position) {
        gradeStorage += (grade - 1) * power(9, position);
    }

    private static int unzipStorage() {
        int result = (int)(gradeStorage % 9 + 1);
        gradeStorage /= 9;
        return result;
    }

    static long gradeStorage = 0;
    public static void main(String[] args) {
        String weekTitle;
        Scanner console = new Scanner(System.in);
        int weekCounter = 0;
        while (weekCounter < WEEKS) {
            weekTitle = console.nextLine();
            if (weekTitle.equals(EOF)) {
                break;
            }
            if (!weekTitle.equals(WEEK_TITLE + (weekCounter + 1))) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int minGrade = 9;
            int currentGrade;
            for (int i = 0; i < 5; ++i) {
                if (!console.hasNextInt()) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                currentGrade = console.nextInt();
                if (currentGrade > 9 || currentGrade < 1) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                if (currentGrade < minGrade) {
                    minGrade = currentGrade;
                }
            }
            createStorage(minGrade, weekCounter);
            console.nextLine();
            ++weekCounter;
        }
        for (int i = 1; i <= weekCounter; ++i) {
            System.out.print(WEEK_TITLE + (i) + " ");
            display(unzipStorage());
        }
    }
}