package streamApi.base;
/*
    =====================================
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.util.*;
import java.util.stream.Collectors;

public class StreamDemo {
    private final List<User> userList = Arrays.asList(
            new User(1, "Michael", "Robert", 37, "TR"),
            new User(2, "Mary", "Patricia", 11, "EN"),
            new User(3, "John", "Michael", 7, "FR"),
            new User(4, "Jenniferrrrrrrrrrrr", "Linda", 77, "TR"),
            new User(5, "William", "Elizabeth", 23, "US"),
            new User(6, "Sue", "Jackson", 11, "IT"),
            new User(7, "Michael", "Tommy", 37, "EN")
    );

    public void foreachTest() {
        System.out.println("\nForEach test: ");
        userList.forEach(System.out::println);
    }

    public void filterTest() {
        System.out.println("\nFilter test:");
        userList.stream().filter(user -> user.getFirstName().contains("M")).collect(Collectors.toSet()).forEach(System.out::println);
    }

    public void mapTest() {
        System.out.println("\nMap test:");
        List<Integer> ids = userList.stream().map(User::getId).map(Long::intValue).toList();
        ids.forEach(System.out::println);

        System.out.println();
        userList.stream().map(user -> new User(user.getId(), "X", "Y", 0, "XY")).forEach(System.out::println);
    }

    public void renameMichaelToNikita() {
        System.out.println("\nrenameMichaelToNikita:");
        userList.stream().map(user -> {
            if (user.getFirstName().equals("Michael")) {
                return new User(user.getId(), "Nikita", user.getLastName(), user.getAge(), user.getNationality());
            }
            return user;
        }).forEach(System.out::println);

        System.out.println("Original:");
        userList.forEach(System.out::println);
    }

    public void sortedTest() {
        System.out.println("\nSort test:");

        System.out.println("asc");
        userList.stream().sorted(Comparator.comparingInt(User::getAge)).forEach(System.out::println);

        System.out.println("desc");
        userList.stream().sorted(Comparator.comparingInt(User::getAge).reversed()).forEach(System.out::println);

        System.out.println("multiple sorting");
        userList.stream().sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName)).forEach(System.out::println);
    }

    public void summaryStatisticsTest() {
        System.out.println("\nStatistics:");

        List<Integer> ages = userList.stream().map(User::getAge).toList();
        int minimalAge = ages.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
        int maximalAge = ages.stream().max(Integer::compare).orElse(Integer.MIN_VALUE);
        double averageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0);
        int sum = ages.stream().mapToInt(Integer::intValue).sum();
        long count = ages.stream().count();
        System.out.println("minimalAge = " + minimalAge
                + "\nmaximalAge = " + maximalAge
                + "\naverageAge = " + averageAge
                + "\nsum = " + sum
                + "\ncount = " + count
        );
    }

    public void displayFirstnameMaxLength() {
        System.out.println("\ndisplayFirstnameMaxLength:");
        int maxFirstnameLength = userList.stream().mapToInt(user -> user.getFirstName().length()).summaryStatistics().getMax();
        System.out.println(maxFirstnameLength);
    }

    public void allMatchTest() {
        System.out.println("\nallMatchTest:");
        boolean isAllAgesGreaterThan76 = userList.stream().allMatch(user -> user.getAge() > 76);
        System.out.println(isAllAgesGreaterThan76);
    }

    public void anyMatchTest() {
        System.out.println("\nanyMatchTest:");
        boolean isAnyAgesGreaterThan76 = userList.stream().anyMatch(user -> user.getAge() > 76);
        System.out.println(isAnyAgesGreaterThan76);
    }

    public void collectionConverterTest() {
            Set<User> userSet = userList.stream().collect(Collectors.toSet());
            LinkedList<User> userLinkedList = userList.stream().collect(Collectors.toCollection(LinkedList::new));
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
    }

    public static void main(String... args) {
        StreamDemo streamDemo = new StreamDemo();

        streamDemo.foreachTest();
        streamDemo.filterTest();
        streamDemo.mapTest();
        streamDemo.sortedTest();
        streamDemo.renameMichaelToNikita();
        streamDemo.summaryStatisticsTest();
        streamDemo.displayFirstnameMaxLength();
        streamDemo.allMatchTest();
        streamDemo.anyMatchTest();

    }
}
