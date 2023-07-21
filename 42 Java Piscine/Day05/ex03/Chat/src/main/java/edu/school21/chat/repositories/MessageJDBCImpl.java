package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;

public class MessageJDBCImpl implements MessageRepository {
    private Statement statement;
    private Connection connection;

    public MessageJDBCImpl(Connection dataSource) {
        try {
            connection = dataSource;
            statement = dataSource.createStatement();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Optional<Message> findById(Long id) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SElECT * FROM chat.messages WHERE id = " + id);

        if(resultSet.next()) {
            long authorId = resultSet.getInt("author");
            long roomId = resultSet.getInt("chatroom");
            long messageId = resultSet.getInt("id");
            Timestamp timestamp = resultSet.getTimestamp("datetime");
            String messageText = resultSet.getString("text");
            ResultSet rs2 = statement.executeQuery("SElECT * FROM chat.users WHERE id = " + authorId);
            rs2.next();
            User user = new User(rs2.getInt("id"), rs2.getString("login"), rs2.getString("password"), null, null);
            ResultSet rs3 = statement.executeQuery("SELECT * FROM chat.chatrooms WHERE id = " + roomId);
            rs3.next();
            Chatroom chatroom = new Chatroom(rs3.getInt("id"), rs3.getString("name"), null, null);
            return Optional.of(new Message(messageId, user, chatroom, messageText, timestamp));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Long save(Message message) throws SQLException {
        validateArgumentForSaveMethod(message.getAuthor());
        validateArgumentForSaveMethod(message.getChatroom());
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO chat.messages(author, chatroom, text, datetime) VALUES (?, ?, ?, ?);");
        preparedStatement.setLong(1, message.getAuthor().getId());
        preparedStatement.setLong(2, message.getChatroom().getId());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setTimestamp(4, message.getTimestamp());
        preparedStatement.execute();

        PreparedStatement idStatement = connection.prepareStatement("SELECT id FROM chat.messages WHERE datetime = ?;");
        idStatement.setTimestamp(1, message.getTimestamp());
        ResultSet resultSet = idStatement.executeQuery();
        resultSet.next();
        return resultSet.getLong("id");
    }

    @Override
    public void update(Message message) throws SQLException {
        validateArgumentForSaveMethod(message.getAuthor());
        validateArgumentForSaveMethod(message.getChatroom());
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE chat.messages " +
                "SET author = ?, chatroom = ?, text = ?, datetime = ? " +
                "WHERE id = " + message.getId());
        preparedStatement.setLong(1, message.getAuthor().getId());
        preparedStatement.setLong(2, message.getChatroom().getId());
        preparedStatement.setString(3, message.getText());
        preparedStatement.setTimestamp(4, message.getTimestamp());
        preparedStatement.execute();
    }

    private void validateArgumentForSaveMethod(User user) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM chat.users WHERE id =" + user.getId());
        if (!resultSet.next()) {
            throw new NotSavedSubEntityException();
        } else {
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            if (!user.getLogin().equals(login) || !user.getPassword().equals(password)) {
                throw new NotSavedSubEntityException();
            }
        }
    }

    private void validateArgumentForSaveMethod (Chatroom chatroom) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM chat.chatrooms WHERE id =" + chatroom.getId());
        if (!resultSet.next()) {
            throw new NotSavedSubEntityException();
        } else  {
            String name = resultSet.getString("name");
            if (!chatroom.getName().equals(name)) {
                throw new NotSavedSubEntityException();
            }
        }
    }
}
