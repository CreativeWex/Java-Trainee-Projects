package io.ylab.intensive.lesson03.DatedMap;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public class DatedMapTest {
    public static void main(String[] args) {
        DatedMap datedMap = new DatedMapImpl();

        System.out.println("================ PUT | GET TESTS ================");
        datedMap.put("one", "1");
        System.out.println("DatedMap value for key 'one': " + datedMap.get("one") + ", time: " + datedMap.getKeyLastInsertionDate("one"));
        System.out.println("DatedMap value for incorrect key: " + datedMap.get("123") + ", time: " + datedMap.getKeyLastInsertionDate("123"));
        datedMap.put("one", "odin");
        System.out.println("DatedMap value for changed key 'one': " + datedMap.get("one") + ", time: " + datedMap.getKeyLastInsertionDate("one"));

        System.out.println("================ CONTAINS KEY TESTS ================");
        System.out.println("DatedMap: Should print true: " + datedMap.containsKey("one"));
        datedMap.remove("one");
        System.out.println("DatedMap: Should print false: " + datedMap.containsKey("one"));
        System.out.println(datedMap.getKeyLastInsertionDate("one"));
    }
}
