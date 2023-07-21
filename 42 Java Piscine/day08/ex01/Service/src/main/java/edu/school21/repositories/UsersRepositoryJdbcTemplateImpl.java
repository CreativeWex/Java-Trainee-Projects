package edu.school21.repositories;

import edu.school21.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id = " + id, new UserMapper()).stream().findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (email) VALUES (?)", entity.getEmail(), new UserMapper());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET email = ? WHERE id = ?", entity.getEmail(), entity.getId(), new UserMapper());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE  FROM users WHERE id = ?", id, new UserMapper());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new UserMapper(), email));
    }
}

class UserMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(rs.getLong("id"), rs.getString("email"));
        return user;
    }
}
