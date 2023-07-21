package edu.school21.repositories;

public class IllegalIdException extends RuntimeException{
    IllegalIdException() {
        super("Id don't found");
    }
}
