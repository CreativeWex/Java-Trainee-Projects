package edu.school21.preprocessor;

import java.util.Locale;

public class PreProcessorToLower implements PreProcessor {
    @Override
    public String process(String msg) {
        String returning = msg.toLowerCase(Locale.ROOT);
        return returning;
    }
}
