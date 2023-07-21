package web.digitallibrary.DAO;
/*
    =====================================
    @project DigitalLibrary
    @created 21/01/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import web.digitallibrary.errors.ResourceNotFoundException;
import web.digitallibrary.mapper.OrderMapper;
import web.digitallibrary.model.Order;

import java.util.List;

@Component
public class OrderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Order> getAll() {
        return jdbcTemplate.query("SELECT orders.id as oid, book_id as bid, client_id as cid, c.name as cname, b.name as bname  FROM orders INNER JOIN " +
                "books b on b.id = orders.book_id INNER JOIN clients c on c.id = orders.client_id", new OrderMapper());
    }

    public void save(Order order) {
        jdbcTemplate.update("INSERT INTO orders (client_id, book_id) VALUES(?, ?)", order.getClientId(), order.getBookId());
        jdbcTemplate.update("UPDATE books SET status = 'Взята' WHERE id = ?", order.getBookId());
    }

    public Order getById(int id) {
        return jdbcTemplate.query("SELECT * FROM orders WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Order.class)).stream().findAny().orElseThrow(() -> new ResourceNotFoundException("Order", "Id", id));
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM orders WHERE id= ? ", id);
    }
}
