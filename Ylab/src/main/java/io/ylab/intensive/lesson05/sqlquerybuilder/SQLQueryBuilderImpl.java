package io.ylab.intensive.lesson05.sqlquerybuilder;
/*
    =====================================
    @project Ylab
    @created 31/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

  private final DataSource dataSource;

  @Autowired
  public SQLQueryBuilderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public String queryForTable(String tableName) throws SQLException {
    try(Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet tablesRS = databaseMetaData.getTables(null, null, tableName, null);
      if (!tablesRS.next()) {
        return null;
      }

      ResultSet columnsRS = databaseMetaData.getColumns(null, null, tableName,  null);
      List<String> columnNames = new ArrayList<>();
      while (columnsRS.next()) {
        columnNames.add(columnsRS.getString("COLUMN_NAME"));
      }
      columnsRS.close();
      return String.format("SELECT %s FROM %s", String.join(",", columnNames), tableName);
    }
  }

  @Override
  public List<String> getTables() throws SQLException {
    try(Connection connection = dataSource.getConnection()) {
      DatabaseMetaData databaseMetaData = connection.getMetaData();
      ResultSet tablesRS = databaseMetaData.getTables(null, null, null, null); // Записи системы
//      ResultSet tablesRS = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"}); Только пользовательские таблицы
      List<String> tableNames = new ArrayList<>();
      while (tablesRS.next()) {
        tableNames.add(tablesRS.getString("TABLE_NAME"));
      }
      return tableNames;
    }
  }
}
