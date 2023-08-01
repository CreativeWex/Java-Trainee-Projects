package lambdas;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.function.Predicate;

public class PredicateDemo {
    public static void main(String[] args) {
        System.out.println("Negative");
        Predicate<Integer> negative = integer -> integer < 0;
        System.out.println(negative.test(-6));
        System.out.println(negative.test(6));
        System.out.println(negative.test(0));

        System.out.println("\nisNull");
        Predicate<String> isNull = s -> s == null;
        System.out.println(isNull.test("Hello"));
        System.out.println(isNull.test(""));
        System.out.println(isNull.test(null));

        System.out.println("\nPredicate.and()");
        Predicate<String> contatainsLetterA = s -> s.contains("a") || s.contains("A");
        Predicate<String> contatainsLetterB = s -> s.contains("b") || s.contains("B");
        // true + true = true
        System.out.println(contatainsLetterA.and(contatainsLetterB).test("abcd"));
        //true + false = false
        System.out.println(contatainsLetterA.and(contatainsLetterB).test("acd"));

        System.out.println("\nPredicate.negate()");
        // true + !false = true
        System.out.println(contatainsLetterA.and(contatainsLetterB.negate()).test("acd"));

        // Строка начинается с 'J' или 'N' и заканчивается на 'A'
        System.out.println("\nJNA");
        Predicate<String> beginsWithJ = s -> s.charAt(0) == 'J';
        Predicate<String> beginsWithN = s -> s.charAt(0) == 'N';
        Predicate<String> endsWithA = s -> s.charAt(s.length() - 1) == 'A';
        System.out.println(beginsWithJ.or(beginsWithN).and(endsWithA).test("JasonA"));
        System.out.println(beginsWithJ.or(beginsWithN).and(endsWithA).test("NasonA"));
        System.out.println(beginsWithJ.or(beginsWithN).and(endsWithA).test("BasonA"));
        System.out.println(beginsWithJ.or(beginsWithN).and(endsWithA).test("JasonB"));
        System.out.println(beginsWithJ.or(beginsWithN).and(endsWithA).test("NasonB"));

        //тестовое 1
        System.out.println("Тестовое 1");
        Predicate<String> predicate1 = t -> {
            System.out.print("predicate1");
            return t.startsWith(" ");
        };
        Predicate<String> predicate2 = t -> {
            System.out.print("predicate2");
            return t.length() > 6;
        };
        predicate1.and(predicate2).test("Hello world!!!");
    }
}
