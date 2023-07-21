package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessageRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    public MessageRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SElECT * FROM chat.messages WHERE id = " + id);

            if(rs.next()) {
                Integer authorId = rs.getInt("author");
                Integer roomId = rs.getInt("room");
                Integer messageId = rs.getInt("id");
                Timestamp time = rs.getTimestamp("message_time");
                String messageText = rs.getString("message_text");
                ResultSet rs2 = stmt.executeQuery("SElECT * FROM chat.users WHERE id = " + authorId);
                rs2.next();
                User user = new User(rs2.getInt("id"), rs2.getString("login"), rs2.getString("password"), null, null);
                ResultSet rs3 = stmt.executeQuery("SELECT * FROM chat.chatrooms WHERE id = " + roomId);
                rs3.next();
                Chatroom chatroom = new Chatroom(rs3.getInt("id"), rs3.getString("name"), null, null);
                Optional<Message> optionalMessage = Optional.of(new Message(messageId, user, chatroom, messageText, time));
                return optionalMessage;
            } else {
                return Optional.empty();
            }
}
}
