package frauds;

import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Менее минуты между зачислением средств и их списанием;

public class MinTimeBeforeDebitAndCredit implements Fraud {
    private final Connection connection;
    private List<User> users;

    public MinTimeBeforeDebitAndCredit(Connection connection, List<User> users) {
        this.connection = connection;
        this.users = users;
    }
    @Override
    public void insertIntoDatabase() throws SQLException {
        Statement createTable = connection.createStatement();
        createTable.executeUpdate("DROP TABLE IF EXISTS fraud_min_time_before_debit_credit; " +
                "CREATE TABLE IF NOT EXISTS fraud_min_time_before_debit_credit" +
                "(transaction_id TEXT PRIMARY KEY REFERENCES transactions(id));");
        createTable.close();
        System.out.println("fraud_min_time_before_debit_credit table has been created");

        HashSet<String> fraudTransactionsIds = getFraudTransactionsIds();
        for (String id : fraudTransactionsIds) {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO fraud_min_time_before_debit_credit(transaction_id) VALUES (?);");
            insert.setString(1, id);
            insert.executeUpdate();
        }
        System.out.println("All data has been inserted into fraud_min_time_before_debit_credit");
    }

    @Override
    public HashSet<String> getFraudTransactionsIds() throws SQLException {
        HashSet<String> fraudTransactionsIds = new HashSet<>();
        for (User user : users) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT oper_type, transaction_date, transaction_id" +
                    " FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id)" +
                    " WHERE client = ? AND oper_type IN('Пополнение', 'Снятие') ORDER BY transaction_date;");
            preparedStatement.setString(1, user.getClientId());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Timestamp> timestamps = new ArrayList<>();
            List<String> types = new ArrayList<>();
            List<String> transactionIds = new ArrayList<>();
            while (resultSet.next()) {
                timestamps.add(resultSet.getTimestamp("transaction_date"));
                types.add(resultSet.getString("oper_type"));
                transactionIds.add(resultSet.getString("transaction_id"));
            }

            for (int i = 1; i < timestamps.size(); i++) {
                if ((types.get(i).equals("Пополнение") && types.get(i - 1).equals("Снятие"))
                        || (types.get(i).equals("Снятие") && types.get(i - 1).equals("Пополнение"))) {
                    long diff = (timestamps.get(i).getTime() - timestamps.get(i - 1).getTime()) / 1000;
                    if (diff < 60) {
                        fraudTransactionsIds.add(transactionIds.get(i));
                        fraudTransactionsIds.add(transactionIds.get(i - 1));
                    }
                }
            }

        }
        return fraudTransactionsIds;
    }
}
