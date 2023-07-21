package web.digitallibrary.DAO;

/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import web.digitallibrary.errors.ResourceNotFoundException;
import web.digitallibrary.mapper.BookMapper;
import web.digitallibrary.model.Author;
import web.digitallibrary.model.Book;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findBooks(int id) {
        return jdbcTemplate.query("SELECT books.id AS bid, books.name AS bname, genres.name AS gname, authors.name" +
                        " AS aname, year, books.description AS bdesc, genres.id AS gid, authors.id AS aid, books.status as bstatus FROM books" +
                        " INNER JOIN genres on books.genre_id = genres.id INNER JOIN authors on authors.id = books.author_id" +
                        " WHERE authors.id = ? ;" , new Object[]{id}, new BookMapper());
    }

    //    =========== CRUD ===========
    public List<Author> getAll() {
        return jdbcTemplate.query("SELECT * FROM authors", new BeanPropertyRowMapper<>(Author.class));
    }

    public Author getById(int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Author", "Id", id));
    }

    public void update(int id, Author newAuthor) {
        jdbcTemplate.update("UPDATE authors SET name=?, dateofbirth=?, dateofdeath=?, description=? WHERE id = ?",
                newAuthor.getName(), newAuthor.getDateOfBirth(), newAuthor.getDateOfDeath(), newAuthor.getDescription(), id);
    }

    public void save(Author author) {
        jdbcTemplate.update("INSERT INTO authors (name, dateofbirth, dateofdeath, description) VALUES(?, ?, ?, ?)",
                author.getName(), author.getDateOfBirth(), author.getDateOfDeath(), author.getDescription());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id=?", id);
    }

    //    =========== Validator ===========

    public Optional<Author> findSimilarName(String name, int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE name= ? AND id <> ?", new Object[]{name, id},
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny();
    }
}
