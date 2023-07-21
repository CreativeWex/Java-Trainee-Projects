package io.ylab.intensive.lesson02.StatsAccumulator;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class StatsAccumulatorTest {
    public static void main(String[] args) {
       StatsAccumulator s = new StatsAccumulatorImpl();
//        s.add(1);
//        s.add(2);
        System.out.println(s.getAvg()); // 1.5 - среднее арифметическое
//        s.add(0);
        System.out.println(s.getMin()); // 0 - минимальное из переданных
//        s.add(3);
//        s.add(8);
        System.out.println(s.getMax()); // 8 - максимальный из переданных
        System.out.println(s.getCount()); // 5 - количество переданных элементов
    }
}
