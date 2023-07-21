package io.ylab.intensive.lesson03.Transliterator;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        System.out.println(transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!"));
        System.out.println(transliterator.transliterate("Only english letters"));
        System.out.println(transliterator.transliterate(""));
        System.out.println(transliterator.transliterate("строчная кириллица"));
        System.out.println(transliterator.transliterate("ПРОПИСНАЯ КИРИЛЛИЦА"));
        System.out.println(transliterator.transliterate("ПОДЪЯРЕМНЫЙ"));
        System.out.println(transliterator.transliterate("ЧАЦКИЙ"));
        System.out.println(transliterator.transliterate("БУЛЬБА"));
        System.out.println(transliterator.transliterate("ЛЮЛЮКОВ"));
        System.out.println(transliterator.transliterate("ЛЯПКИН-ТЯПКИН"));
    }
}
