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
import web.digitallibrary.mapper.OrderMapper;
import web.digitallibrary.model.Book;
import web.digitallibrary.model.Order;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setFree(int id) {
        jdbcTemplate.update("UPDATE books SET status = 'Свободна' WHERE id = ?", id);
    }

    public Order findOrder(int id) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE book_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Order.class)).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Book", "Id", id));
    }


    //    =========== CRUD ===========

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT books.id AS bid, books.name AS bname, books.status AS bstatus, genres.name AS gname, authors.name" +
                        " AS aname, year, books.description AS bdesc, genres.id AS gid, authors.id AS aid FROM books" +
                        " INNER JOIN genres on books.genre_id = genres.id INNER JOIN authors on authors.id = books.author_id;" ,
                new BookMapper());
    }

    public Book getById(int id) {
        return jdbcTemplate.query("SELECT books.id AS bid, books.name AS bname, books.status AS bstatus, genres.name AS gname, authors.name" +
                        " AS aname, year, books.description AS bdesc, genres.id AS gid, authors.id AS aid FROM books" +
                        " INNER JOIN genres on books.genre_id = genres.id INNER JOIN authors on authors.id =" +
                        " books.author_id WHERE books.id = ?", new Object[]{id},
                new BookMapper()).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Author", "Id", id));
    }

    public void update(int id, Book newBook) {
        jdbcTemplate.update("UPDATE books SET name=?, genre_id=?, author_id=?, year=?, description=? WHERE id = ?",
                newBook.getName(), newBook.getGenreId(), newBook.getAuthorId(), newBook.getYear(),
                newBook.getDescription(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books (name, genre_id, author_id, year, description, status) VALUES(?, ?, ?, ?, ?, ?)",
                book.getName(), book.getGenreId(), book.getAuthorId(), book.getYear(), book.getDescription(), "Свободна");
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    //    =========== Validator ===========

    public Optional<Book> findSimilarName(String name, int id) {
        return jdbcTemplate.query("SELECT books.id AS bid, books.name AS bname, books.status AS bstatus, genres.name AS gname, authors.name" +
                        " AS aname, year, books.description AS bdesc, genres.id AS gid, authors.id AS aid FROM books" +
                        " INNER JOIN genres on books.genre_id = genres.id INNER JOIN authors on authors.id =" +
                        " books.author_id WHERE books.name = ? AND books.id <> ?", new Object[]{name, id},
                new BookMapper()).stream().findAny();
    }
}
