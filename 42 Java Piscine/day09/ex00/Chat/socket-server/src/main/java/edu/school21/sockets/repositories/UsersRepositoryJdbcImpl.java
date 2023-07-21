package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository{

    private final JdbcTemplate jdbcTemplate;
@Autowired
    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM users WHERE id IN('%s')" + id, new UserMapper()).stream().findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (name, password) VALUES (?, ?)", entity.getName(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET name = ?, password = ? WHERE id = ?", entity.getName(), entity.getPassword(), entity.getId(), new UserMapper());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE  FROM users WHERE id = ?", id, new UserMapper());
    }

    @Override
    public Optional<User> findByName(String name) {
        User user = jdbcTemplate.query(String.format("SELECT * FROM users WHERE name IN('%s');", name), new UserMapper()).stream().findFirst().orElse(null);
        return (Optional.ofNullable(user));
    }
}

class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("password"));
        return user;
    }
}
