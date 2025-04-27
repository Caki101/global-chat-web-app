package com.study.global_chat.controllers;

import com.study.global_chat.classes.IncomingMessage;
import com.study.global_chat.classes.Message;
import com.study.global_chat.services.Dbs;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class MessageController {
    private final Dbs dbs;

    public MessageController(Dbs dbs) {
        this.dbs = dbs;
    }

    @MessageMapping("/send_message")
    @SendTo("/topic/messages")
    public List<Message> message(IncomingMessage message) {
        Message msg = new Message(message.getFrom(), message.getMessage(), new Timestamp(System.currentTimeMillis()));

        dbs.saveMessage(msg);

        return List.of(msg);
    }
}
