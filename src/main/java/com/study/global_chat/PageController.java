package com.study.global_chat;

import com.study.global_chat.chat_server.IncomingMessage;
import com.study.global_chat.classes.Message;
import com.study.global_chat.mongodb_communication.MongoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

import java.sql.Timestamp;
import java.util.List;

@Controller
public class PageController {
    @Autowired
    private MongoServices mongoServices;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @MessageMapping("/send_message")
    @SendTo("/topic/messages")
    public List<Message> message(IncomingMessage message) throws Exception {
        Message msg = new Message(message.getFrom(), message.getMessage(), new Timestamp(System.currentTimeMillis()).toString());

        mongoServices.saveMessage(msg);

        return List.of(msg);
    }
}
