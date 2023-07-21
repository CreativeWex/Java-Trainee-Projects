package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);

    // TESTS
    persistentMap.init("first_map");
    System.out.println(persistentMap.containsKey("one")); //false
    persistentMap.put("one", "1");
    System.out.println(persistentMap.containsKey("one")); //true

    persistentMap.init("second_map");
    System.out.println(persistentMap.containsKey("one")); //false
    persistentMap.put("one", "1");
    System.out.println(persistentMap.containsKey("one")); //true

    persistentMap.put("two", "2");
    persistentMap.init("first_map");
    System.out.println(persistentMap.containsKey("two")); //false

    persistentMap.init("second_map");
    List<String> secondMapKeys = persistentMap.getKeys();
    System.out.print("\nsecond_map's keys: ");
    for (String key : secondMapKeys) {
      System.out.print(key + " ");
    }

    persistentMap.init("first_map");
    List<String> firstMapKeys = persistentMap.getKeys();
    System.out.print("\nfirst_map's keys: ");
    for (String key : firstMapKeys) {
      System.out.print(key + " ");
    }
    System.out.println("\ndeleting fist_map's key 'one'");
    persistentMap.remove("one");
    firstMapKeys = persistentMap.getKeys();
    System.out.print("first_map's keys: ");
    for (String key : firstMapKeys) {
      System.out.print(key + " ");
    }
    System.out.println();

    persistentMap.init("second_map");
    System.out.println("deleting second_map's key 'two'");
    persistentMap.remove("two");
    secondMapKeys = persistentMap.getKeys();
    System.out.print("second_map's keys: ");
    for (String key : secondMapKeys) {
      System.out.print(key + " ");
    }
    System.out.println();

    System.out.println("\nput/get tests:");
    System.out.println(persistentMap.get("one")); // 1
    persistentMap.put("one", "first");
    System.out.println(persistentMap.get("one")); // first
    persistentMap.put("three", "3");
    System.out.println(persistentMap.get("3")); // null
    System.out.println(persistentMap.get("three")); // 3

    System.out.println("\nclear test:");
    persistentMap.clear();
    secondMapKeys = persistentMap.getKeys();
    System.out.print("second_map's keys: ");
    for (String key : secondMapKeys) {
      System.out.print(key + " ");
    }
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
