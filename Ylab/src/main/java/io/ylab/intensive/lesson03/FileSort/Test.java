package io.ylab.intensive.lesson03.FileSort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 5_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        Long startTime = System.currentTimeMillis();
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println("Время сортировки: " + (System.currentTimeMillis() - startTime) + "мс");
        System.out.println(new Validator(sortedFile).isSorted()); // true
//        dataFile.deleteOnExit();
    }
}
