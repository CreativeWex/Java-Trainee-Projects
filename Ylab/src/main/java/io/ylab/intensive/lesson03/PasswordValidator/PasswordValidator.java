package io.ylab.intensive.lesson03.PasswordValidator;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import io.ylab.intensive.lesson03.PasswordValidator.exceptions.WrongLoginException;
import io.ylab.intensive.lesson03.PasswordValidator.exceptions.WrongPasswordException;

public class PasswordValidator {
    private static final int LOGIN_MAX_LENGTH = 19;
    private static final int PASSWORD_MAX_LENGTH = 19;

    public static boolean validate(String login, String password, String confirmPassword) {
        try {
            if (!login.matches("[a-zA-Z0-9_]+")) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (login.length() > LOGIN_MAX_LENGTH) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            if (!password.matches("[a-zA-Z0-9_]+")) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (password.length() > PASSWORD_MAX_LENGTH) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
