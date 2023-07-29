package string;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class StringBuilderDemo {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("Hello world!");

        stringBuilder.delete(1, 6);
        System.out.println(stringBuilder);

        stringBuilder.replace(1, 5, "=============");
        System.out.println(stringBuilder);
    }
}
