package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FileSortImpl implements FileSorter {
  private final DataSource dataSource;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public File createSortedFile() {
    File sortedFile = new File("sortedData.txt");
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT val FROM numbers ORDER BY val DESC");
         PrintWriter writer = new PrintWriter(sortedFile);
         ResultSet resultSet = statement.executeQuery();) {
      while (resultSet.next()) {
        writer.println(resultSet.getLong("val"));
      }
      writer.flush();
    } catch (IOException | SQLException e) {
      System.err.println("Creating sorted file error " + e.getMessage());
    }
    return sortedFile;
  }

  public File sortSimple(File data) {
    try (FileInputStream fileInputStream = new FileInputStream(data);
         Scanner scanner = new Scanner(fileInputStream);
         Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers(val) VALUES (?)")) {
      while (scanner.hasNextLine()) {
        statement.setLong(1, Long.parseLong(scanner.nextLine()));
        statement.execute();
      }
    } catch (IOException | SQLException e) {
      System.err.println("Simple sorting error " + e.getMessage());
    }
    return createSortedFile();
  }

  @Override
  public File sort(File data) {
    try (FileInputStream fileInputStream = new FileInputStream(data);
         Scanner scanner = new Scanner(fileInputStream);
         Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("INSERT INTO numbers(val) VALUES (?)")) {
      while (scanner.hasNextLine()) {
        statement.setLong(1, Long.parseLong(scanner.nextLine()));
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (IOException | SQLException e) {
      System.err.println("Sorting error " + e.getMessage());
    }
    return createSortedFile();
  }
}
