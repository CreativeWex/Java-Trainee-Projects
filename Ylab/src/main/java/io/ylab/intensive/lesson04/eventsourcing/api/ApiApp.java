package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.util.List;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_RESET = "\u001B[0m";

    ConnectionFactory connectionFactory = initMQ();
    try (Connection connection = connectionFactory.newConnection()) {
      PersonApi personApi = new PersonApiImpl(connection);

      System.out.println(ANSI_YELLOW + "Empty list test" + ANSI_RESET);
      List<Person> personList = personApi.findAll();
      System.out.println("Person list:");
      for (Person person : personList) {
        System.out.println(person);
      }

      System.out.println(ANSI_YELLOW + "\nFind not existing person test:" + ANSI_RESET);
      System.out.println("Result finding person with id 100: " + personApi.findPerson(100L));

      System.out.println(ANSI_YELLOW + "\nFind existing person test:" + ANSI_RESET);
      personApi.savePerson(1L, "Nikita", "Bereznev", "Sergeevich");
      personApi.savePerson(2L, "Peter", "Green", "John");
      Thread.sleep(100);
      System.out.println("Result finding person with id 1: " + personApi.findPerson(1L));
      System.out.println("Result finding person with id 2: " + personApi.findPerson(2L));
      System.out.println();

      System.out.println(ANSI_YELLOW + "Not empty list test" + ANSI_RESET);
      personApi.savePerson(1L, "Nikita", "Bereznev", "Sergeevich");
      personApi.savePerson(1L, "Nikita", "-", "-");
      personApi.savePerson(10L, "Nikita", "Bereznev", "Sergeevich");
      personList = personApi.findAll();
      System.out.println("Person list:");
      for (Person person : personList) {
        System.out.println(person);
      }

      System.out.println(ANSI_YELLOW + "\nDeleting person test" + ANSI_RESET);
      personApi.deletePerson(10L);
      Thread.sleep(100);
      System.out.println("Invoking method findPerson(10), result:");
      System.out.println(personApi.findPerson(10L));

      System.out.println("Deleting not existing person");
      personApi.deletePerson(10L);
    }
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
