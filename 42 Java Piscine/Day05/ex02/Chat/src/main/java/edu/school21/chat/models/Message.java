package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private long id;
    private User author;
    private Chatroom chatroom;
    private String text;
    private Timestamp timestamp;

    public Message(long id, User author, Chatroom chatroom, String text, Timestamp timestamp) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
        this.text = text;
        this.timestamp = timestamp;
    }

    public Message() {}

    @Override
    public int hashCode() {
        return Objects.hash(id, author, chatroom, text, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.getId() && author.equals(message.getAuthor()) && chatroom.equals(message.getChatroom())
                && text.equals(message.getText()) && timestamp.equals(message.getTimestamp());
    }

    @Override
    public String toString() {
        return "Message {" +
                "\n\tid=" + id +
                "\n\tauthor=" + author +
                "\n\tchatroom=" + chatroom +
                "\n\ttext='" + text +
                "\n\tdateTime='" + timestamp +
                "\n}";
    }

    public long getId() {
        return this.id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getChatroom() {
        return chatroom;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }



    public void setAuthor(User author) {
        this.author = author;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }
}
