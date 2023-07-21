package io.ylab.intensive.lesson02.SnilsValidator;
/*
    =====================================
    @project 2-Ylab-OOP
    @created 12/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class SnilsValidatorImpl implements SnilsValidator{
    @Override
    public boolean validate(String snils) {
        if (!snils.matches("^[0-9]{11}$")) {
            return false;
        }
        int sum = 0;
        int checkDigit = -1;

        for (int i = 0; i < 9; i ++) {
            sum += Character.digit(snils.charAt(i), 10) * (9 - i);
        }
        if (sum < 100) {
            checkDigit = sum;
        } else if (sum > 101) {
            checkDigit = sum % 101;
            if (checkDigit == 100) {
                checkDigit = 0;
            }
        }
        return checkDigit == Integer.parseInt(snils.substring(9));
    }
}
