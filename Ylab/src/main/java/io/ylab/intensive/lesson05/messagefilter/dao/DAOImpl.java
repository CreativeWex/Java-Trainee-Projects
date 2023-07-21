package io.ylab.intensive.lesson05.messagefilter.dao;
/*
    =====================================
    @project Ylab
    @created 01/04/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

@Component
public class DAOImpl implements DAO{
  private final DataSource dataSource;
  private final String TABLE_NAME = "filter_dictionary";

  @Autowired
  public DAOImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public boolean isTableExists() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet tableRS = databaseMetaData.getTables(null, null, TABLE_NAME, null);
      return tableRS.next();
    }
  }

  @Override
  public void createTable() {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("CREATE TABLE "+ TABLE_NAME + "(id BIGSERIAL PRIMARY KEY, word text);")) {
      if (isTableExists()) {
        return;
      }
      statement.execute();
    } catch (SQLException e) {
      System.err.println("Table " + TABLE_NAME +" creating exception: " + e.getMessage());
    }
  }

  @Override
  public void clearTable() {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM " + TABLE_NAME + ";")) {
      statement.execute();
    } catch (SQLException e) {
      System.err.println("Clearing ' " + TABLE_NAME + "' exception: " + e.getMessage());
    }
  }

  @Override
  public void loadDataFromFile(File file) {
    createTable();
    clearTable();
    try(FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream);
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO " + TABLE_NAME + "(word) VALUES(?);")) {
      while (scanner.hasNextLine()) {
        statement.setString(1, scanner.nextLine());
        statement.addBatch();
      }
      statement.executeBatch();
    } catch (IOException e) {
      System.err.println("File opening error: " + file);
    } catch (SQLException e) {
      System.err.println("loadDataFromFile - SQLException error: " + e.getMessage());
    }
  }

  @Override
  public boolean isWordBanned(String word) {
    try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE word = ?")) {
      statement.setString(1, word);
      ResultSet resultSet = statement.executeQuery();
      return resultSet.next();
    } catch (SQLException e) {
      System.err.println("isWordBanned exception: " + e.getMessage());
    }
    return false;
  }
}
