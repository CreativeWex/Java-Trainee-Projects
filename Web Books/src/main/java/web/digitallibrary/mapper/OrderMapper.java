package web.digitallibrary.mapper;
/*
    =====================================
    @project DigitalLibrary
    @created 21/01/2023    
    @author Bereznev Nikita @CreativeWex
    =====================================
 */

import org.springframework.jdbc.core.RowMapper;
import web.digitallibrary.model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();

        order.setId(rs.getInt("oid"));
        order.setBookId(rs.getInt("bid"));
        order.setClientId(rs.getInt("cid"));
        order.setBookName(rs.getString("bname"));
        order.setClientName(rs.getString("cname"));

        return order;
    }
}
