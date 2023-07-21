package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class MessageJDBCImpl implements MessageRepository {
    private final String MESSAGE_QUERY = "SElECT * FROM chat.messages WHERE id = ";
    private final String USER_QUERY = "SElECT * FROM chat.users WHERE id = ";
    private final String CHATROOM_QUERY = "SElECT * FROM chat.chatrooms WHERE id = ";
    private Statement statement;

    public MessageJDBCImpl(Connection dataSource) {
        try {
            statement = dataSource.createStatement();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        ResultSet rs = statement.executeQuery("SElECT * FROM chat.messages WHERE id = " + id);

        if(rs.next()) {
            long authorId = rs.getInt("author");
            long roomId = rs.getInt("chatroom");
            long messageId = rs.getInt("id");
            Timestamp timestamp = rs.getTimestamp("datetime");
            String messageText = rs.getString("text");
            ResultSet rs2 = statement.executeQuery("SElECT * FROM chat.users WHERE id = " + authorId);
            rs2.next();
            User user = new User(rs2.getInt("id"), rs2.getString("login"), rs2.getString("password"), null, null);
            ResultSet rs3 = statement.executeQuery("SELECT * FROM chat.chatrooms WHERE id = " + roomId);
            rs3.next();
            Chatroom chatroom = new Chatroom(rs3.getInt("id"), rs3.getString("name"), null, null);
            Optional<Message> optionalMessage = Optional.of(new Message(messageId, user, chatroom, messageText, timestamp));
            return optionalMessage;
        } else {
            return Optional.empty();
        }
    }

    private User createUserObjectFromQuery(long authorId) throws Exception {
        try {
            ResultSet resultSet = statement.executeQuery(USER_QUERY + authorId);
            if (resultSet.next()) {
                return new User(authorId, resultSet.getString("login"), resultSet.getString("password"), null, null);
            }
            return new User(authorId, null, null, null, null);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    private Chatroom createChatroomObjectFromQuery(long chatroomId) throws Exception {
        try {
            ResultSet resultSet = statement.executeQuery(CHATROOM_QUERY + chatroomId);
            if (resultSet.next()) {
                return new Chatroom(chatroomId, resultSet.getString("name"), null, null);
            }
            return new Chatroom(chatroomId, null, null, null);
        } catch (Exception exception) {
            throw new Exception(exception);
        }
    }
}
