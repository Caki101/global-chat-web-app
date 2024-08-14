package com.study.global_chat.mongodb_communication;

import com.study.global_chat.classes.Message;
import com.study.global_chat.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoServices {
    @Autowired
    MessagesRepository messagesRepository;
    @Autowired
    UsersRepository usersRepository;

    public List<Message> getMessages () {
        return messagesRepository.findAll();
    }

    public void saveMessage (Message message) {
        messagesRepository.save(message);
    }

    public List<User> getUsers () {
        return usersRepository.findAll();
    }

    public void saveUser (User user) {
        usersRepository.save(user);
    }

    public User getUser (String username) {
        return usersRepository.findUserByUsername(username);
    }
}
