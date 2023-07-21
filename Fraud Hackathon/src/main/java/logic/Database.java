package logic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import models.Transaction;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/open_cup";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";
    private static Connection connection;

    public Database() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(DB_URL);
            config.setUsername(DB_USERNAME);
            config.setPassword(DB_PASSWORD);

            HikariDataSource hikariDataSource = new HikariDataSource(config);
            if (hikariDataSource.getConnection() == null) {
                throw new SQLException("Database connection failed");
            }
            connection = hikariDataSource.getConnection();
            System.out.println("Database successfully connected");
        }
        catch (Exception exception) {
            throw new RuntimeException("Connection failed from createDataSourceConnection: " + exception);
        }
    }

    public void prepareSetup() throws SQLException {

        Statement createTransactions = connection.createStatement();
        createTransactions.executeUpdate("DROP TABLE IF EXISTS fraud_expensive_transactions;" +
                "DROP TABLE IF EXISTS fraud_expensive_transactions_month;" +
                "DROP TABLE IF EXISTS fraud_many_transactions_a_day;" +
                "DROP TABLE IF EXISTS fraud_min_time_before_debit_credit;" +
                "CREATE TABLE IF NOT EXISTS transactions (\n" +
                "    id TEXT PRIMARY KEY,\n" +
                "    transaction_date TIMESTAMP NOT NULL,\n" +
                "    card TEXT NOT NULL,\n" +
                "    account TEXT NOT NULL,\n" +
                "    account_valid_to TIMESTAMP NOT NULL,\n" +
                "    oper_type TEXT NOT NULL,\n" +
                "    amount NUMERIC(10,2) NOT NULL,\n" +
                "    oper_result TEXT NOT NULL,\n" +
                "    terminal TEXT NOT NULL,\n" +
                "    terminal_type TEXT NOT NULL,\n" +
                "    city TEXT NOT NULL,\n" +
                "    address TEXT NOT NULL\n" +
                ");");
        System.out.println("Created table transactions");
        createTransactions.close();

        Statement createUsers = connection.createStatement();
        createUsers.executeUpdate("CREATE TABLE IF NOT EXISTS users (\n" +
                "     id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY ,\n" +
                "     client TEXT NOT NULL,\n" +
                "     last_name TEXT NOT NULL,\n" +
                "     first_name TEXT NOT NULL,\n" +
                "     patronymic TEXT,\n" +
                "     date_of_birth TIMESTAMP NOT NULL,\n" +
                "     passport TEXT NOT NULL,\n" +
                "     passport_valid_to TIMESTAMP NOT NULL,\n" +
                "     phone TEXT,\n" +
                "     transaction_id TEXT REFERENCES transactions(id)\n" +
                ");" +
                "DELETE FROM users;" +
                "DELETE FROM transactions;");
        System.out.println("Created table users");
        createUsers.close();
    }
    public void fillDatabaseFromList(List<Transaction> list) throws SQLException {
        int clientsAmount = 0;
        int transactionsAmount = 0;

        for (Transaction transaction : list) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO transactions" +
                    "(id, transaction_date, card, account, account_valid_to, oper_type, oper_result ,amount, terminal, terminal_type, city, address)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, transaction.getId());
            preparedStatement.setTimestamp(2, transaction.getTransactionDate());
            preparedStatement.setString(3, transaction.getCard());
            preparedStatement.setString(4, transaction.getAccount());
            preparedStatement.setTimestamp(5, transaction.getAccountValidTo());
            preparedStatement.setString(6, transaction.getOperationType());
            preparedStatement.setString(7, transaction.getOperationResult());
            preparedStatement.setDouble(8, transaction.getAmount());
            preparedStatement.setString(9, transaction.getTerminal());
            preparedStatement.setString(10, transaction.getTerminalType());
            preparedStatement.setString(11, transaction.getCity());
            preparedStatement.setString(12, transaction.getAddress());
            transactionsAmount += preparedStatement.executeUpdate();
        }
        System.out.println("Amount of transactions lines in database:\t" + transactionsAmount);

        for (Transaction transaction : list) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users" +
                    "(client, last_name, first_name, patronymic, date_of_birth, passport, passport_valid_to, phone, transaction_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
            User client = transaction.getClient();
            preparedStatement.setString(1, client.getClientId());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getFirstName());
            preparedStatement.setString(4, client.getPatronymic());
            preparedStatement.setTimestamp(5, client.getDateOfBirth());
            preparedStatement.setString(6, client.getPassport());
            preparedStatement.setTimestamp(7, client.getPassportValidTo());
            preparedStatement.setString(8, client.getPhone());
            preparedStatement.setString(9, transaction.getId());
            clientsAmount += preparedStatement.executeUpdate();
        }
        System.out.println("Amount of client lines in database:\t" + clientsAmount);
    }
    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
