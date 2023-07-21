package edu.school21.chat.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessageJDBCImpl;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chat";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static final String DB_SCHEMA = "/resources/schema.sql";
    private static final String DB_DATA = "/resources/data.sql";

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


    public static void main(String[] args) throws FileNotFoundException, SQLException {
        databaseConnection = createDataSourceConnection();
        System.out.println("Database successfully connected");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message ID");
        if (!scanner.hasNextLong()) {
            System.out.println("Invalid id format");
            System.exit(-1);
        }
        Long id = scanner.nextLong();

        MessageJDBCImpl jdbcRepository = new MessageJDBCImpl(databaseConnection);
        Message message = jdbcRepository.findById(id).isPresent() ? jdbcRepository.findById(id).get() : new Message();
        System.out.println(message);
    }
}