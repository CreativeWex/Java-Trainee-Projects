package edu.school21.repositories;

import edu.school21.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository{

    DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        Statement stmt = null;
        try (Connection connection = dataSource.getConnection()) {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + id);

            if (rs.next()) {
                Long userId = rs.getLong("id");
                String email = rs.getString("email");
                user = new User(userId, email);
            } else {
                throw new IllegalIdException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = null;

        try (Connection connection = dataSource.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            users = new ArrayList<>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String email = rs.getString("email");
                User user = new User(id, email);
                users.add(user);
            }
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User entity) {
        PreparedStatement stmt = null;
        try(Connection connection = dataSource.getConnection()) {
            stmt = connection.prepareStatement(
                    "INSERT INTO users (email) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getEmail());

            stmt.executeUpdate();
            ResultSet resultSet = stmt.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong("id");
            entity.setId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        PreparedStatement stmt = null;
        try (Connection connection = dataSource.getConnection()) {
            stmt = connection.prepareStatement(
                    "UPDATE users SET email = ? WHERE id = ?");
            stmt.setString(1, entity.getEmail());
            stmt.setLong(2, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        PreparedStatement stmt = null;
        try (Connection connection = dataSource.getConnection()){
            stmt = connection.prepareStatement(
                    "DELETE  FROM users WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalProduct = Optional.empty();

        Statement stmt = null;
        try (Connection connection = dataSource.getConnection()){
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = " + email);

            if (rs.next()) {
                Long userId = rs.getLong("id");
                String resultEmail = rs.getString("email");
                optionalProduct = Optional.of(new User(userId, resultEmail));
            } else {
                throw new IllegalIdException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return optionalProduct;
    }
}
