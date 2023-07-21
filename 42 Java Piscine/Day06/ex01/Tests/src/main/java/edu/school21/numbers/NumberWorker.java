package edu.school21.numbers;

import edu.school21.numbers.IllegalNumberException;

public class NumberWorker {
    public boolean isPrime(int number) {
        if(number < 2) {
            throw new IllegalNumberException();
        }
        int i = 2;
        while (i * i <= number) {
            if (number % i == 0) {
                return false;
            }
            i++;
        }
        return true;
    }
    public int digitSum(int number) {
        int sum = 0;
        number = Math.abs(number);
        if(number == -2147483648) {
            return 47;
        }
        while(number/10 != 0) {
            sum += number % 10;
            number /= 10;
        }
        sum += number;
        return sum;
    }
}
