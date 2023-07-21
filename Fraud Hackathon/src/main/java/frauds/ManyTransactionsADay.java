package frauds;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;

// Более 10 операций в день
public class ManyTransactionsADay implements Fraud{
    private final Connection connection;
    public ManyTransactionsADay(Connection connection) {
        this.connection = connection;
    }
    private LinkedHashSet<String> findUserId() throws SQLException {
        Statement statement = connection.createStatement();
        LinkedHashSet<String> userIds = new LinkedHashSet<>();

        ResultSet resultSet = statement.executeQuery("SELECT client FROM users GROUP BY client HAVING COUNT(*) > 10;");
        while (resultSet.next()) {
            userIds.add(resultSet.getString("client"));
        }

        statement.close();
        return userIds;
    }

    private LinkedHashSet<String> findDatesWhenTransactionsMade(String userId) throws SQLException {
        LinkedHashSet<String> dates = new LinkedHashSet<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_date FROM users " +
                "INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE client = ?;");
        preparedStatement.setString(1, userId);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String tmp = resultSet.getString("transaction_date");
            dates.add(tmp.substring(0, tmp.length() - 9));
        }

        preparedStatement.close();
        return dates;
    }

    private int countTransactionsAmountPerDay(String date, String userId) throws SQLException {
        date += " __:__:__";
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT transaction_date FROM users" +
                " INNER JOIN transactions ON(users.transaction_id = transactions.id) WHERE transaction_date::text" +
                " LIKE ? AND client = ?;");
        preparedStatement.setString(1, date);
        preparedStatement.setString(2, userId);
        ResultSet resultSet = preparedStatement.executeQuery();

        int amount = 0;
        while (resultSet.next()) {
            amount++;
        }
        return amount;
    }

    @Override
    public HashSet<String> getFraudTransactionsIds() throws SQLException {
        LinkedHashSet<String> suspiciousUsersIds = findUserId();
        HashMap<String, String> approvedUsersAndTransactionDates = new HashMap<>();

        for (String userId : suspiciousUsersIds) {
            LinkedHashSet<String> dates = findDatesWhenTransactionsMade(userId);
            for (String date : dates) {
                int amount = countTransactionsAmountPerDay(date, userId);
                if (amount > 10) {
                    approvedUsersAndTransactionDates.put(userId, date);
                }
            }
        }
        HashSet<String> fraudTrancationsIds = new HashSet<>();
        for (Map.Entry entry : approvedUsersAndTransactionDates.entrySet()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT transactions.id" +
                    " FROM users INNER JOIN transactions ON(users.transaction_id = transactions.id)" +
                    " WHERE transaction_date::text LIKE ? AND client = ?;");
            preparedStatement.setString(1, entry.getValue()+ " __:__:__");
            preparedStatement.setString(2, (String) entry.getKey());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fraudTrancationsIds.add(resultSet.getString("id"));
            }
        }
        return fraudTrancationsIds;
    }

    @Override
    public void insertIntoDatabase() throws SQLException {
        Statement createTable = connection.createStatement();
        createTable.executeUpdate("DROP TABLE IF EXISTS fraud_many_transactions_a_day; " +
                "CREATE TABLE IF NOT EXISTS fraud_many_transactions_a_day" +
                "(transaction_id TEXT PRIMARY KEY REFERENCES transactions(id));");
        createTable.close();
        System.out.println("fraud_many_transactions_a_day table has been created");

        HashSet<String> transactionsIds = getFraudTransactionsIds();
        for (String id : transactionsIds) {
            PreparedStatement insert = connection.prepareStatement("INSERT INTO fraud_many_transactions_a_day(transaction_id) VALUES (?);");
            insert.setString(1, id);
            insert.executeUpdate();
        }
        System.out.println("All data has been inserted into fraud_many_transactions_a_day");
    }

    private void displayMap(HashMap<String, String> userDate) {
        for (Map.Entry entry : userDate.entrySet()) {
            System.out.println("ID: " + entry.getKey() + "; Date: " + entry.getValue());
        }
    }
}