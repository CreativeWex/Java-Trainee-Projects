package io.ylab.intensive.lesson03.PasswordValidator.exceptions;
/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 19/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super();
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
