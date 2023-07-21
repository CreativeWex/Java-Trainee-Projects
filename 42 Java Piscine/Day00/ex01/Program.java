import java.util.Scanner;

public class Program {
    static int stepsCounter = 1;
    static public int sqrt(long num) {
        int i = 1;
        while (i * i < num) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        if (!input.hasNextInt()) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        long num = input.nextInt();
        if (num < 2) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        for (int i = 2; i < sqrt(num) + 1; i++) {
            if (num % i == 0) {
                System.out.print("false " + stepsCounter);
                System.exit(0);
            }
            stepsCounter++;
        }
        System.out.print("true " + (stepsCounter - 1));
        System.exit(0);
    }
}
