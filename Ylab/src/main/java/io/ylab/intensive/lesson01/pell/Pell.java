package io.ylab.intensive.lesson01.pell;
/*
    =====================================
    @project Ylab
    @created 03/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int first = 0, second = 1;
            for (int i = 0; i < n; i++) {
                int tmp = 2 * second + first;
                first = second;
                second = tmp;
            }
            System.out.println(first);
        }
    }

}
