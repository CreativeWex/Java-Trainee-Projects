package app;

import frauds.*;
import logic.Database;
import logic.ResultWriter;
import models.Transaction;
import org.json.simple.parser.ParseException;
import logic.Parser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) throws IOException, ParseException, SQLException {
        Parser parser = new Parser();
        List<Transaction> transactions = parser.getTransactions();

        System.out.println("======================================");
        Database database = new Database();
        database.prepareSetup();
        database.fillDatabaseFromList(transactions);
        System.out.println("======================================");

        ManyTransactionsADay manyTransactionsADay = new ManyTransactionsADay(Database.getConnection());
        manyTransactionsADay.insertIntoDatabase();
        System.out.println();

        MinTimeBeforeDebitAndCredit minTimeBeforeDebitAndCredit = new MinTimeBeforeDebitAndCredit(Database.getConnection(), parser.getUsers());
        minTimeBeforeDebitAndCredit.insertIntoDatabase();
        System.out.println();

        ExpensiveTransactionsInDay expensiveTransactionsInDay = new ExpensiveTransactionsInDay(Database.getConnection());
        expensiveTransactionsInDay.insertIntoDatabase();
        System.out.println();

        ExpensiveTransactionsInMonth expensiveTransactionsInMonth = new ExpensiveTransactionsInMonth(Database.getConnection());
        expensiveTransactionsInMonth.insertIntoDatabase();
        System.out.println("======================================");

        ResultWriter resultWriter = new ResultWriter(Database.getConnection(),
                manyTransactionsADay.getFraudTransactionsIds(), minTimeBeforeDebitAndCredit.getFraudTransactionsIds(),
                expensiveTransactionsInDay.getFraudTransactionsIds(), expensiveTransactionsInMonth.getFraudTransactionsIds());
        resultWriter.createResultFile();
        System.out.println("Result file created");
        Database.closeConnection();
    }
}