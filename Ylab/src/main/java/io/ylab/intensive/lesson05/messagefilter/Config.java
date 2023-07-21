package io.ylab.intensive.lesson05.messagefilter;

import javax.sql.DataSource;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.dao.DAOImpl;
import io.ylab.intensive.lesson05.messagefilter.rabbitMQ.MessageManagerImpl;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter")
public class Config {
  
  @Bean
  public ConnectionFactory connectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost("localhost");
    connectionFactory.setPort(5672);
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    connectionFactory.setVirtualHost("/");
    return connectionFactory;
  }
  
  @Bean
  public DataSource dataSource() {
    PGSimpleDataSource dataSource = new PGSimpleDataSource();
    dataSource.setServerName("localhost");
    dataSource.setUser("postgres");
    dataSource.setPassword("postgres");
    dataSource.setDatabaseName("postgres");
    dataSource.setPortNumber(5432);
    return dataSource;
  }

  @Bean
  public DAOImpl daoImpl() {
    return new DAOImpl(dataSource());
  }

  @Bean
  public MessageManagerImpl messageManagerImpl() {
    return new MessageManagerImpl(connectionFactory(), daoImpl());
  }

}
