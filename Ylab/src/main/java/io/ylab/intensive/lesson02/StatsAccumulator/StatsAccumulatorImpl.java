package io.ylab.intensive.lesson02.StatsAccumulator;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class StatsAccumulatorImpl implements StatsAccumulator{
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int count = 0;
    double sum = 0;

    @Override
    public void add(int value) {
        if (value > max) {
            max = value;
        }
        if (value < min) {
            min = value;
        }
        count++;
        sum += value;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            return 0.0;
        }
        return  sum / count;
    }
}
