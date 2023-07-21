package io.ylab.intensive.lesson03.OrgStructure;/*
    =====================================
    @project Ylab-3-Collections-Files
    @created 18/03/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    public Employee parseStructure(File csvFile) throws IOException;
}
