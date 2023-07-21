package edu.school21.repositories;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

public class TestEmbeddedDataSource {

    EmbeddedDatabase dataSource;
    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        if (dataSource == null) {
            System.out.println("NULL");
        }
    }

    @Test
    public void checkConnection() throws SQLException {
        Assertions.assertNotNull(dataSource.getConnection());
    }

    @AfterEach
    public void shutdown() {
        dataSource.shutdown();
    }
}
