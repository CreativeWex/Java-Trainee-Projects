package io.ylab.intensive.lesson03.OrgStructure;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.io.File;
import java.io.IOException;
import java.util.List;

public class OrgStructureParserTest {
    public static void main(String[] args) throws IOException {
        OrgStructureParser orgStructureParser = new OrgStructureParserImpl();
        Employee ceo = orgStructureParser.parseStructure(new File("src/main/java/org/ylab/OrgStructure/data.csv"));
        System.out.println("\nГенеральный директор:\n" + ceo + "\n");

        System.out.println("Прямые подчиненные:");
        List<Employee> ceoSubordinate = ceo.getSubordinate();
        for (Employee employee : ceoSubordinate) {
            System.out.println(employee);
            if (!employee.getSubordinate().isEmpty()) {
                System.out.println("\tРаботники в распоряжении: ");
                for (Employee subEmployee : employee.getSubordinate()) {
                    System.out.println("\t" + subEmployee);
                }
            } else {
                System.out.println("\tНет работников в распоряжении");
            }
            System.out.println();
        }
    }
}
