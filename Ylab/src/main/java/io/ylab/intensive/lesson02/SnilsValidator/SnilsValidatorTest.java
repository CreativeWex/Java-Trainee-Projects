package io.ylab.intensive.lesson02.SnilsValidator;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("awdawd")); //false
        System.out.println(new SnilsValidatorImpl().validate("123")); //false
        System.out.println(new SnilsValidatorImpl().validate("9011440444111")); //false
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
        System.out.println(new SnilsValidatorImpl().validate("16492541082")); //true
        System.out.println(new SnilsValidatorImpl().validate("16492541183")); //true
    }
}
