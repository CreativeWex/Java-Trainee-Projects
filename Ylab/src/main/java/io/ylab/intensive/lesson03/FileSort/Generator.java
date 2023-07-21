package io.ylab.intensive.lesson03.FileSort;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 19/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator {
    public File generate(String name, int count) throws IOException {
        Random random = new Random();
        File file = new File(name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < count; i++) {
                pw.println(random.nextLong());
            }
            pw.flush();
        }
        return file;
    }

}
