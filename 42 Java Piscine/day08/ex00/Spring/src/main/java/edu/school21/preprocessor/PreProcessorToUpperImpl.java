package edu.school21.preprocessor;

import java.util.Locale;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String msg) {
        String returnString = msg.toUpperCase(Locale.ROOT);
        return returnString;
    }
}
