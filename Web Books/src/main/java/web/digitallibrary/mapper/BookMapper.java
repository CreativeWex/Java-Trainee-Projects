package web.digitallibrary.mapper;
/*
    =====================================
    @project DigitalLibrary
    @created 12/01/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.jdbc.core.RowMapper;
import web.digitallibrary.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();

        book.setId(rs.getInt("bid"));
        book.setName(rs.getString("bname"));
        book.setGenre(rs.getString("gname"));
        book.setAuthor(rs.getString("aname"));
        book.setYear(rs.getInt("year"));
        book.setDescription(rs.getString("bdesc"));
        book.setAuthorId(rs.getInt("gid"));
        book.setGenreId(rs.getInt("bid"));
        book.setStatus(rs.getString("bstatus"));

        return book;
    }
}
