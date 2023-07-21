package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessageJDBCImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Program {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chat";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";

    private static Connection databaseConnection;
    private static Connection createDataSourceConnection() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USERNAME);
            config.setPassword(DB_PASSWORD);

            HikariDataSource hikariDataSource = new HikariDataSource(config);
            if (hikariDataSource.getConnection() == null) {
                throw new SQLException("Database connection failed");
            }
            return hikariDataSource.getConnection();
        }
        catch (Exception exception) {
            throw new RuntimeException("Connection failed from createDataSourceConnection: " + exception);
        }
    }

    public static void main(String[] args) throws SQLException {
        databaseConnection = createDataSourceConnection();
        System.out.println("Database successfully connected");

        User creator = new User(3, "hamchur", "HxSQGYQ1", new ArrayList<>(), new ArrayList<>());
        Chatroom chatroom = new Chatroom(3, "memes", creator, new ArrayList<>());
        Message message = new Message(0, creator, chatroom, "testing save method", new Timestamp(System.currentTimeMillis()));

        MessageJDBCImpl jdbc = new MessageJDBCImpl(databaseConnection);
        System.out.println(jdbc.save(message));
    }
}