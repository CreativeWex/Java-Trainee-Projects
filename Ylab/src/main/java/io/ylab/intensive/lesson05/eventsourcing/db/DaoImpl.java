package io.ylab.intensive.lesson05.eventsourcing.db;
/*
    =====================================
    @project Ylab
    @created 27/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DaoImpl implements Dao {

  private final DataSource dataSource;

  @Autowired
  public DaoImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  private boolean isPersonExists(Long id) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement isExistsStatement = connection.prepareStatement("SELECT * FROM person WHERE person_id = " + id);
         ResultSet isExistsResult = isExistsStatement.executeQuery()) {
      if (isExistsResult.next()) {
        return true;
      }
    } catch (SQLException e) {
      System.err.println("isPersonExists error " + e.getMessage());
    }
    return false;
  }

  @Override
  public void save(Person person) {
    if (isPersonExists(person.getId())) {
      String query = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? WHERE person_id = ?";
      try (Connection connection = dataSource.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, person.getName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setString(3, person.getMiddleName());
        preparedStatement.setLong(4, person.getId());
        preparedStatement.executeUpdate();
        System.out.println("Person updated " + person.getId());
      } catch (SQLException e) {
        System.err.println("Updating person error " + e.getMessage());
      }
    } else {
      try (Connection connection = dataSource.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO person(person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?)")) {
        preparedStatement.setLong(1, person.getId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setString(4, person.getMiddleName());
        preparedStatement.executeUpdate();
        System.out.println("person saved, id: " + person.getId());
      } catch (SQLException e) {
        System.err.println("Saving person error " + e.getMessage());
      }
    }
  }

  @Override
  public void delete(Long id) {
    if (!isPersonExists(id)) {
      System.err.format("deleting failed: no such person, id: %d\n", id);
    } else {
      try(Connection connection = dataSource.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE person_id = ?")) {
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
      } catch (SQLException e) {
        System.err.println("Deleting person error, id: " + id);
      }
      System.out.println("person deleted, id: " + id);
    }
  }
}
