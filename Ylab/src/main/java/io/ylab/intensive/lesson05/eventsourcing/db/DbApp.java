package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DbApp {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    // тут пишем создание и запуск приложения работы с БД
    Dao dao = applicationContext.getBean(Dao.class);
    ConnectionFactory connectionFactory = applicationContext.getBean(ConnectionFactory.class);

    String queueName = "queue";
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      try(Connection connection = connectionFactory.newConnection();
          Channel channel = connection.createChannel()) {
        channel.queueDeclare(queueName, true, false, false, null);
        while (!Thread.currentThread().isInterrupted()) {
          Thread.sleep(100);
          GetResponse message = channel.basicGet(queueName, true);
          if (message != null) {
            String routingKey = message.getEnvelope().getRoutingKey();
            System.out.println("Incoming request id = " + new String(message.getBody()) + " : " + routingKey);
            if (routingKey.equals("save")) {
              dao.save(objectMapper.readValue(message.getBody(), Person.class));
            } else if (routingKey.equals("delete")) {
              dao.delete(objectMapper.readValue(message.getBody(), Long.class));
            }
          }
        }
      } catch (IOException | TimeoutException e) {
        System.out.println("Waiting for active queue...");
        Thread.sleep(3000);
      }
    }
  }
}