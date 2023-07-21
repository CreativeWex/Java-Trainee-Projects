package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

import io.ylab.intensive.lesson04.DbUtil;

public class FileSorterTest {
  public static void main(String[] args) throws SQLException, IOException {
    DataSource dataSource = initDb();
    File data = new File("data.txt");
    FileSorter fileSorter = new FileSortImpl(dataSource);

    long startTime = System.currentTimeMillis();
    File res = fileSorter.sort(data);
    System.out.format("Batch processing sorting %d ms\n", System.currentTimeMillis() - startTime);

    FileSortImpl simpleSort = new FileSortImpl(dataSource);
    startTime = System.currentTimeMillis();
    res = simpleSort.sortSimple(data);
    System.out.format("Simple sorting %d ms\n", System.currentTimeMillis() - startTime);
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
}
