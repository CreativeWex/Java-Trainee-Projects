package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() {

        List<Product> products = null;

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");
            products = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Integer price = rs.getInt("price");
                Product product = new Product(id, name, price);
                products.add(product);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {

        Optional<Product> optionalProduct = Optional.empty();

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products WHERE id = " + id);

            if (rs.next()) {
                Long productId = rs.getLong("id");
                String name = rs.getString("name");
                Integer price = rs.getInt("price");
                optionalProduct = Optional.of(new Product(productId, name, price));
            } else {
                throw new IllegalIdException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optionalProduct;
    }

    @Override
    public void update(Product product) {

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE products SET name = ?, price = ? WHERE id = ?");
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getPrice());
            stmt.setLong(3, product.getId());

            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void save(Product product) {

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "INSERT INTO products (name, price) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getPrice());

            stmt.executeUpdate();

            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong("id");

            product.setId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {

        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE  FROM products WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, id);

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
