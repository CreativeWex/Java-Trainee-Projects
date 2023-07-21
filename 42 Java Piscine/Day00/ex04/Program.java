import java.util.Scanner;

public class Program {
    private static final int MAX_SYMBOLS_COUNT = 10;
    private static final int SYMBOLS_IN_UNICODE = 65536;
    private static final int BAR_COUNT = 10;

    public static void printGraph(char[] letters, int[] eachLetterAmount){
        int max = eachLetterAmount[0];
        System.out.println();
        System.out.println();
        for(int i = 0; i < MAX_SYMBOLS_COUNT; i++) {
            if(eachLetterAmount[i] * BAR_COUNT / max == 10) {
                System.out.print(eachLetterAmount[i] + "\t");
            }
        }
        System.out.println();
        for (int i = MAX_SYMBOLS_COUNT; i > 0; i--) {
            for (int j = 0; j < MAX_SYMBOLS_COUNT; j++) {
                if(eachLetterAmount[j] != 0) {
                    if (eachLetterAmount[j] * BAR_COUNT / max >= i) {
                        System.out.print("#\t");
                    }
                    if (eachLetterAmount[j] * BAR_COUNT / max == i - 1) {
                        System.out.print(eachLetterAmount[j] + "\t");
                    }
                }
            }
            System.out.println();
        }
        for(int i = 0; i < MAX_SYMBOLS_COUNT; i++) {
            if(eachLetterAmount[i] != 0) {
                System.out.print(letters[i] + "\t");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">_");
        String inputLine = scanner.nextLine();
        if (inputLine.length() == 0) {
            System.err.println("Empty string");
            System.exit(-1);
        }

        int[] eachCharFrequency = new int[SYMBOLS_IN_UNICODE];
        char[] inputArr = inputLine.toCharArray();
        for (int i = 0; i < inputLine.length(); i++) {
            eachCharFrequency[inputArr[i]]++;
        }

        char[] tenSortedChars = new char[MAX_SYMBOLS_COUNT];
        int[] tenSortedCharsAmount = new int[MAX_SYMBOLS_COUNT];
        for (int j = 0; j < MAX_SYMBOLS_COUNT; j++) {
            int max = eachCharFrequency[0];
            for (int i = 0; i < SYMBOLS_IN_UNICODE; ++i) {
                if (max < eachCharFrequency[i]) {
                    max = eachCharFrequency[i];
                    tenSortedChars[j] = (char) i;
                }
            }
            tenSortedCharsAmount[j] = max;
            eachCharFrequency[(int) tenSortedChars[j]] = 0;
        }

        printGraph(tenSortedChars, tenSortedCharsAmount);
    }
}