package frauds;

import java.sql.SQLException;
import java.util.HashSet;

public interface Fraud {
    public void insertIntoDatabase() throws SQLException;
    public HashSet<String> getFraudTransactionsIds() throws SQLException;
}