package com.study.global_chat.chat_server;

public class IncomingMessage {
    String from;
    String message;

    public IncomingMessage() {
    }

    public IncomingMessage(String from, String message) {
        this.from = from;
        this.message = message;
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

    @Override
    public String toString() {
        return "IncomingMessage{" +
                "from='" + from + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
