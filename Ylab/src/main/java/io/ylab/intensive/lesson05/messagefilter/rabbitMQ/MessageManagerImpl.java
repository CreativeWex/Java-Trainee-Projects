package io.ylab.intensive.lesson05.messagefilter.rabbitMQ;
/*
    =====================================
    @project Ylab
    @created 01/04/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.messagefilter.dao.DAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageManagerImpl implements MessageManager {

  private final DAO dao;
  private final ConnectionFactory connectionFactory;
  private static final String DATA_FILE_PATH = "src/main/java/io/ylab/intensive/lesson05/messagefilter/filterDictionary.txt";
  private static final String EXCHANGE_NAME = "exchange";
  private static final String INPUT_QUEUE_NAME = "input";
  private static final String OUTPUT_QUEUE_NAME = "output";

  @Autowired
  public MessageManagerImpl(ConnectionFactory connectionFactory, DAO dao) {
    this.connectionFactory = connectionFactory;
    this.dao = dao;
  }

  @Override
  public void filterQueueMessages() throws InterruptedException {
    File dataFile = new File(DATA_FILE_PATH);
    dao.loadDataFromFile(dataFile);

    while (true) {
      try(Connection connection = connectionFactory.newConnection();
          Channel channel = connection.createChannel()) {
        channel.queueDeclare(INPUT_QUEUE_NAME, true, false, false, null);
        while (!Thread.currentThread().isInterrupted()) {
          GetResponse response = channel.basicGet(INPUT_QUEUE_NAME, true);
          if (response != null) {
            String message = new String(response.getBody());
            System.out.println("Uncensored message: " + message);
            String censoredMessage = censor(message);
            System.out.println("Censored message: " + censoredMessage);
            queueOutput(censoredMessage);
          }
        }
      } catch (IOException | TimeoutException e) {
        System.out.println("Waiting for active queue...");
        Thread.sleep(3000);
      }
    }
  }

  @Override
  public String censor(String message) {
    String[] words = message.split("((?=[\t\n .,;:\\-!?`\"])|(?<=[\t\n .,;:\\-!?`\"]))");
    StringBuilder censoredMessage = new StringBuilder();
    for (String word : words) {
      if (dao.isWordBanned(word.toLowerCase())) {
        censoredMessage.append(word.charAt(0)).append("*".repeat(word.length() - 2)).append(word.charAt(word.length() - 1));
      } else {
        censoredMessage.append(word);
      }
    }
    return censoredMessage.toString();
  }

  @Override
  public void queueOutput(String message) {
    String routingKey = "censoredMessage";
    try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
      channel.queueDeclare(OUTPUT_QUEUE_NAME, true, false, false, null);
      channel.queueBind(OUTPUT_QUEUE_NAME, EXCHANGE_NAME, routingKey);

      channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
    } catch (IOException | TimeoutException e) {
      System.err.format("Queue output error (%s): %s", OUTPUT_QUEUE_NAME, e.getMessage());
    }
  }
}
