package io.ylab.intensive.lesson05.eventsourcing.api;
;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApi personApi = applicationContext.getBean(PersonApi.class);
    // пишем взаимодействие с PersonApi

    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_RESET = "\u001B[0m";

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
    Thread.sleep(2000);
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