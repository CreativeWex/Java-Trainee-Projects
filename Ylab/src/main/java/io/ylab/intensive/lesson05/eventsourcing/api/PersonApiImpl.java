package io.ylab.intensive.lesson05.eventsourcing.api;
/*
    =====================================
    @project Ylab
    @created 31/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public class PersonApiImpl implements PersonApi{
  private final ConnectionFactory connectionFactory;
  private final DataSource dataSource;
  private static final String EXCHANGE_NAME = "personExchange";
  private static final String QUEUE_NAME = "queue";

  @Autowired
  public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
    this.connectionFactory = connectionFactory;
    this.dataSource = dataSource;
  }

  @Override
  public void deletePerson(Long personId) {
    try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
      String routingKey = "delete";

      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

      channel.basicPublish(EXCHANGE_NAME, routingKey, null, personId.toString().getBytes());
      System.out.println("request sent: delete " + personId);
    } catch (IOException | TimeoutException e) {
      System.err.println("Person deleting request error " + e.getMessage());
    }
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    Person person = new Person();
    person.setId(personId);
    person.setName(firstName);
    person.setLastName(lastName);

    person.setMiddleName(middleName);
    try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      String routingKey = "save";
      String personJSON = new ObjectMapper().writeValueAsString(person);

      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);

      channel.basicPublish(EXCHANGE_NAME, routingKey, null, personJSON.getBytes());
      System.out.println("Request sent: save " + person);
    } catch (IOException | TimeoutException e) {
      System.err.println("Person saving request error " + e.getMessage());
    }
  }

  @Override
  public Person findPerson(Long personId) {
    Person person = new Person();
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE person_id = " + personId);
         ResultSet resultSet = statement.executeQuery()) {
      if (!resultSet.next()) {
        System.err.println("There is no person with id: " + personId);
        return null;
      }
      person.setId(resultSet.getLong("person_id"));
      person.setName(resultSet.getString("first_name"));
      person.setLastName(resultSet.getString("last_name"));
      person.setMiddleName(resultSet.getString("middle_name"));
    } catch (SQLException e) {
      System.err.println("Person finding error " + personId);
    }
    return person;
  }

  @Override
  public List<Person> findAll() {
    List<Person> personList = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM person");
         ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Person person = new Person();
        person.setId(resultSet.getLong("person_id"));
        person.setName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setMiddleName(resultSet.getString("middle_name"));
        personList.add(person);
      }
    } catch (SQLException e) {
      System.err.println("FindAll error " + e.getMessage());
    }
    return personList;
  }
}
