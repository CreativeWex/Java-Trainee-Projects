package io.ylab.intensive.lesson02.ComplexNumbers;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber onlyReal = new ComplexNumber(13);
        ComplexNumber positive = new ComplexNumber(4, 3);
        ComplexNumber negative = new ComplexNumber(25, -100);

        System.out.format("%s +\n %s\n -------------------\n %s\n\n\n", onlyReal, positive, onlyReal.add(positive));
        System.out.format("%s +\n %s\n -------------------\n %s\n\n\n", positive, negative, positive.add(negative));

        System.out.format("%s -\n %s\n -------------------\n %s\n\n\n", onlyReal, positive, onlyReal.subtract(positive));
        System.out.format("%s -\n %s\n -------------------\n %s\n\n\n", positive, negative, positive.subtract(negative));

        System.out.format("%s *\n %s\n -------------------\n %s\n\n\n", onlyReal, positive, onlyReal.multiply(positive));
        System.out.format("%s *\n %s\n -------------------\n %s\n\n\n", positive, negative, positive.multiply(negative));

        System.out.format("|%s| = %s\n\n\n", onlyReal, onlyReal.abs());
        System.out.format("|%s| = %s\n\n\n", positive, positive.abs());
        System.out.format("|%s| = %s\n\n\n", negative, negative.abs());


    }
}
