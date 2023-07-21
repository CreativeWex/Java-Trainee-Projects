package frauds;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;

public class ExpensiveTransactionsInDay implements Fraud{
    private final Connection connection;

    public ExpensiveTransactionsInDay(Connection connection) {
        this.connection = connection;
    }
    private LinkedList<String> findUserId() throws SQLException {
        LinkedList<String> usersId = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT client FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, t.transaction_date::date\n" +
                "HAVING sum(amount) > 100000;");

        while (resultSet.next()) {
            usersId.add(resultSet.getString("client"));
        }
        statement.close();
        return usersId;
    }

    private LinkedList<String> findDate() throws SQLException {
        LinkedList<String> date = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT t.transaction_date::date date FROM users\n" +
                "INNER JOIN transactions t on t.id = users.transaction_id\n" +
                "WHERE t.oper_result LIKE 'Успешно'\n" +
                "GROUP BY client, t.transaction_date::date\n" +
                "HAVING sum(amount) > 100000;");
        while (resultSet.next()) {
            date.add(resultSet.getString("date"));
        }

        statement.close();
        return date;
    }

    @Override
    public HashSet<String> getFraudTransactionsIds() throws SQLException {
        LinkedList<String> suspiciousUsersId = findUserId();
        LinkedList<String> suspiciousDate = findDate();
        HashSet<String> fraudTransactionsIds = new HashSet<>();
        for (int i = 0; i < suspiciousUsersId.size(); i++) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT t.id FROM transactions t\n" +
                            "INNER JOIN users u on t.id = u.transaction_id\n" +
                            "WHERE oper_result LIKE 'Успешно' AND client = ? AND transaction_date::date = ?;");

            preparedStatement.setString(1, suspiciousUsersId.get(i));
            preparedStatement.setDate(2, Date.valueOf(suspiciousDate.get(i)));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                fraudTransactionsIds.add(resultSet.getString("id"));
            }
        }
        return fraudTransactionsIds;
    }

    @Override
    public void insertIntoDatabase() throws SQLException {
        Statement createTable = connection.createStatement();
        createTable.executeUpdate("CREATE TABLE IF NOT EXISTS fraud_expensive_transactions" +
                "(transaction_id TEXT PRIMARY KEY REFERENCES transactions(id));");
        System.out.println("fraud_expensive_transactions_a_day table has been created");

        HashSet<String> transactionsIds = getFraudTransactionsIds();
        for (String id : transactionsIds) {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO fraud_expensive_transactions(transaction_id) VALUES (?);");
            insert.setString(1, id);
            insert.executeUpdate();
        }
        System.out.println("All data has been inserted into fraud_expensive_transactions_a_day");
        createTable.close();
    }
}