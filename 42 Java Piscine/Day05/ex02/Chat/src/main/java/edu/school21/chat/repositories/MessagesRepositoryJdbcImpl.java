package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
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
                Long messageId = rs.getLong("id");
                LocalDateTime time = rs.getTimestamp("message_time").toLocalDateTime();
                String messageText = rs.getString("message_text");
                ResultSet rs2 = stmt.executeQuery("SElECT * FROM chat.users WHERE id = " + authorId);
                rs2.next();
                User user = new User(rs2.getLong("id"), rs2.getString("login"), rs2.getString("password"), null, null);
                ResultSet rs3 = stmt.executeQuery("SELECT * FROM chat.chatrooms WHERE id = " + roomId);
                rs3.next();
                Chatroom chatroom = new Chatroom(rs3.getLong("id"), rs3.getString("name"), null, null);
                Optional<Message> optionalMessage = Optional.of(new Message(messageId, user, chatroom, messageText, time));
                return optionalMessage;
            } else {
                return Optional.empty();
            }
}

    @Override
    public void save(Message message) throws SQLException {
        try {
        Connection connection = dataSource.getConnection();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO chat.messages (author, room, message_text) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        stmt.setLong(1, message.getAuthor().getId());
        stmt.setLong(2, message.getRoom().getId());
        stmt.setString(3, message.getMessageText());

        stmt.executeUpdate();

        ResultSet resultSet = stmt.getGeneratedKeys();
        resultSet.next();
        Long id = resultSet.getLong("id");

        message.setId(id);
        } catch (SQLException e) {
            throw new NotSavedSubEntityException();
        }

    }
}
