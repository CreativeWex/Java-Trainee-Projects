package edu.school21.sockets.repositories;

public class IllegalIdException extends RuntimeException{
    IllegalIdException() {
        super("Id don't found");
    }
}
