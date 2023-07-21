package io.ylab.intensive.lesson03.OrgStructure;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class OrgStructureParserImpl implements OrgStructureParser {

    private final Map<Long, Employee> idEmployeeMap = new HashMap<>();

    @Override
    public Employee parseStructure(File csvFile) throws IOException {
        Employee ceo = new Employee();
        try (FileInputStream fileInputStream = new FileInputStream(csvFile)) {
            Scanner scanner = new Scanner(fileInputStream);
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] fields = scanner.nextLine().split(";");
                Employee employee = new Employee();
                employee.setId(Long.valueOf(fields[0]));
                if (!fields[1].isEmpty()) {
                    employee.setBossId(Long.valueOf(fields[1]));
                }
                employee.setName(fields[2]);
                employee.setPosition(fields[3]);
                idEmployeeMap.put(employee.getId(), employee);
            }
            scanner.close();

            for (Employee employee : idEmployeeMap.values()) {
                Long bossId = employee.getBossId();
                if (bossId != null) {
                    Employee boss = idEmployeeMap.get(bossId);
                    if (boss != null) {
                        employee.setBoss(boss);
                        boss.getSubordinate().add(employee);
                    }
                } else {
                    ceo = employee;
                }
            }
        }
        return ceo;
    }
}
