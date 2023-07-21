package io.ylab.intensive.lesson01.multTable;/*
    =====================================
    @project Ylab
    @created 03/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class MultTable {
    public static void main(String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                System.out.format("%d x %d = %d\n", i, j, i * j);
            }
        }
    }
}
