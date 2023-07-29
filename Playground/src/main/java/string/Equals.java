package string;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class Equals {
    public static void main(String[] args) {
        String str1 = "one";
        String str2 = "one";
        String str3 = new String("one");

        System.out.println(str1 == str2);
        System.out.println(str3 == str1);
    }
}
