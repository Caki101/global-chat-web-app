package com.study.global_chat.classes;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages", schema = "public")
public class Message {
    @SequenceGenerator(name = "msg_id_seq", sequenceName = "msg_id_seq", allocationSize = 1, initialValue = 0)
    @GeneratedValue(generator = "msg_id_seq", strategy = GenerationType.SEQUENCE)
    @Id
    Long id;

    @Column(name = "\"from\"")
    String from;

    String message;
    Timestamp timestamp;

    public Message() {
    }

    public Message(String from, String message, Timestamp timestamp) {
        this.from = from;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
