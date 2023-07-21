package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.messagefilter.rabbitMQ.MessageManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    MessageManager messageManager = applicationContext.getBean(MessageManager.class);
    try {
      messageManager.filterQueueMessages();
    } catch (InterruptedException e) {
      System.err.println("Error reading queue " + e.getMessage());
    }
  }
}
