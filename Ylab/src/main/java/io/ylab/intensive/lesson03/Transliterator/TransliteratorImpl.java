package io.ylab.intensive.lesson03.Transliterator;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 17/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator{

    private Map<Character, String> dictionary = new HashMap<>();

    // Организаторы в чате разрешили захардкодить
    // Map.of() не поддерживает больше 4 ключей
    public TransliteratorImpl() {
        dictionary.put('А', "A");
        dictionary.put('Б', "B");
        dictionary.put('В', "V");
        dictionary.put('Г', "G");
        dictionary.put('Д', "D");
        dictionary.put('Е', "E");
        dictionary.put('Ё', "E");
        dictionary.put('Ж', "ZH");
        dictionary.put('З', "Z");
        dictionary.put('И', "I");
        dictionary.put('Й', "I");
        dictionary.put('К', "K");
        dictionary.put('Л', "L");
        dictionary.put('М', "M");
        dictionary.put('Н', "N");
        dictionary.put('О', "O");
        dictionary.put('П', "P");
        dictionary.put('Р', "R");
        dictionary.put('С', "S");
        dictionary.put('Т', "T");
        dictionary.put('У', "U");
        dictionary.put('Ф', "F");
        dictionary.put('Х', "KH");
        dictionary.put('Ц', "TS");
        dictionary.put('Ч', "CH");
        dictionary.put('Ш', "SH");
        dictionary.put('Щ', "SHCH");
        dictionary.put('Ы', "Y");
        dictionary.put('Ь', "");
        dictionary.put('Ъ', "IE");
        dictionary.put('Э', "E");
        dictionary.put('Ю', "IU");
        dictionary.put('Я', "IA");
    }

    @Override
    public String transliterate(String source) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char currentLetter = source.charAt(i);
            if (dictionary.containsKey(currentLetter)) {
                result.append(dictionary.get(currentLetter));
            } else {
                result.append(currentLetter);
            }
        }
        return result.toString();
    }
}
