package edu.school21.numbers;

public class IllegalNumberException extends RuntimeException {
    IllegalNumberException() {
        super("Number should be greater than 1");
    }
}
