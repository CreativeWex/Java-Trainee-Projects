package lambdas;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringSorting {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(List.of("gg", "z", "zzz", "a", "aa", "ab", "aab", "f", "g"));
        System.out.println("unsorted = " + words);

        Collections.sort(words, (str1, str2) -> str1.compareTo(str2));
        System.out.println("sorted by asc = " + words);

        Collections.sort(words, (str1, str2) -> str2.compareTo(str1));
        System.out.println("sorted by desc = " + words);
    }
}
