package io.ylab.intensive.lesson03.PasswordValidator.exceptions;
/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class WrongLoginException extends RuntimeException {
    public WrongLoginException() {
        super();
    }

    public WrongLoginException(String message) {
        super(message);
    }
}
