package logic;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashSet;

public class ResultWriter {
    private Connection connection;
    private final String RESULT_FILE_NAME = "result.txt";
    HashSet<String> manyTransADayIds;
    HashSet<String> minTimeTransIds;
    HashSet<String> expensiveTransIds; // TODO:
    HashSet<String> expensiveMonthTransIds; // TODO:


    public ResultWriter(Connection connection, HashSet<String> manyTransADayIds, HashSet<String> minTimeTransIds,
                        HashSet<String> expensiveTransIds, HashSet<String> expensiveMonthTransIds) {
        this.connection = connection;
        this.manyTransADayIds = manyTransADayIds;
        this.minTimeTransIds = minTimeTransIds;
        this.expensiveTransIds = expensiveTransIds;
        this.expensiveMonthTransIds = expensiveMonthTransIds;
    }

    public void createResultFile() throws IOException {
        FileWriter fileWriter = new FileWriter(RESULT_FILE_NAME);

        fileWriter.write("Более 10 операций в день\n");
        for (String id : manyTransADayIds) {
            fileWriter.write(id + ", ");
        }
        fileWriter.write("\n\n");

        fileWriter.write("Менее минуты между зачислением средств и их списанием;\n");
        for (String id : minTimeTransIds) {
            fileWriter.write(id + ", ");
        }
        fileWriter.write("\n\n");

        fileWriter.write("Операции по зачислению безналичных средств между физлицами в объеме более 100 000 руб.;\n");
        for (String id : expensiveTransIds) {
            fileWriter.write(id + ", ");
        }
        fileWriter.write("\n\n");

        fileWriter.write("в день или 1 млн руб. в месяц;\n");
        for (String id : expensiveMonthTransIds) {
            fileWriter.write(id + ", ");
        }
        fileWriter.write("\n\n");

        fileWriter.close();
    }


}
