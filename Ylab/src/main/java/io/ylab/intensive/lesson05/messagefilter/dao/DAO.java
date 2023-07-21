package io.ylab.intensive.lesson05.messagefilter.dao;
/*
    =====================================
    @project Ylab
    @created 01/04/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.io.File;
import java.sql.SQLException;

public interface DAO {
  public boolean isTableExists() throws SQLException;
  public void createTable();
  public void clearTable();
  public void loadDataFromFile(File file);

  public boolean isWordBanned(String word);
}
