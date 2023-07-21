package io.ylab.intensive.lesson04.eventsourcing.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public class DbApp {
  public static void main(String[] args) throws Exception {
    String queueName = "queue";
    ConnectionFactory connectionFactory = initMQ();
    ObjectMapper objectMapper = new ObjectMapper();

    while (true) {
      try(Connection connection = connectionFactory.newConnection();
          Channel channel = connection.createChannel()) {
        Dao dao = new DaoImpl(initDb().getConnection());
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

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
  
  private static DataSource initDb() throws SQLException {
    String ddl = "" 
                     + "drop table if exists person;" 
                     + "create table if not exists person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
