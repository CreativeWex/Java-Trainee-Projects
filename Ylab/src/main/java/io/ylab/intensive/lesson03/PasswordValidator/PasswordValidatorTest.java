package io.ylab.intensive.lesson03.PasswordValidator;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 19/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class PasswordValidatorTest {
    public static void main(String[] args) {
        System.out.println(PasswordValidator.validate("", "", ""));
        System.out.println(PasswordValidator.validate("login", "", ""));
        System.out.println(PasswordValidator.validate("login12_", "password31_", "password31_"));
        System.out.println(PasswordValidator.validate("_12", "_", "_"));
        System.out.println(PasswordValidator.validate("login", "password", "password") + "\n");

        System.out.println(PasswordValidator.validate("login1111111111111111111111111111111", "password", "password"));
        System.out.println(PasswordValidator.validate("login12_", "password31_111111111111111111111111111111111111", "password31_"));
        System.out.println(PasswordValidator.validate("login12_", "password31_", "paa")  + "\n");

        System.out.println(PasswordValidator.validate("@login12_", "@password31_", "password31_"));
        System.out.println(PasswordValidator.validate("login12_", "@password31_", "password31_"));
    }
}
