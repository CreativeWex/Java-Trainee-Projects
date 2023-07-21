package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductsRepositoryJdbcImplTest {
    EmbeddedDatabase dataSource;
    ProductsRepository productsRepository;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "orange", 100),
            new Product(2L, "lemon", 220),
            new Product(3L, "banana", 50),
            new Product(4L, "apple", 45),
            new Product(5L, "burger", 200));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "orange", 100);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3L, "banana", 50);

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        try {
            productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void checkFindAll() {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    public void checkFindById() {
        Long id = 1L;
        try {
            Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(id).get());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void checkfindByIdThrowException() {
        assertThrows(RuntimeException.class, ()-> {
            productsRepository.findById(5000L);
        });
    }

    @Test
    public void checkUpdate() {
        Product product = new Product(3L, "banana", 50);
        productsRepository.update(product);
        try {
            Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(product.getId()).get());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void checkSave() {
        Integer countBefore = productsRepository.findAll().size();
        Product product = new Product(6L, "apple", 45);
        productsRepository.save(product);
        Integer countAfter = productsRepository.findAll().size();
        Assertions.assertEquals(countBefore, countAfter - 1);
    }

    @Test
    public void checkDelete() {
        Integer countBefore = productsRepository.findAll().size();
        productsRepository.delete(5L);
        Integer countAfter = productsRepository.findAll().size();
        Assertions.assertEquals(countBefore, countAfter + 1);
    }

    @AfterEach
    public void end() {
        dataSource.shutdown();
    }

}
