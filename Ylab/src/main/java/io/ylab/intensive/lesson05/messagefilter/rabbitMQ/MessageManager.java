package io.ylab.intensive.lesson05.messagefilter.rabbitMQ;
/*
    =====================================
    @project Ylab
    @created 02/04/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

public interface MessageManager {
  public void filterQueueMessages() throws InterruptedException;
  public String censor(String message);
  public void queueOutput(String message);
}
