package com.study.global_chat.services;

import com.study.global_chat.classes.Message;
import com.study.global_chat.classes.User;
import com.study.global_chat.postgresql_comms.MessagesRepository;
import com.study.global_chat.postgresql_comms.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Dbs {
    private final MessagesRepository messagesRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public Dbs(MessagesRepository messagesRepository,
               UsersRepository usersRepository) {
        this.messagesRepository = messagesRepository;
        this.usersRepository = usersRepository;
    }

    public void saveMessage(Message message) {
        messagesRepository.save(message);
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messagesRepository.findAll().forEach(messages::add);
        return messages;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(String username) {
        User user = usersRepository.findByUsername(username);

        if (user == null) {
            System.err.println("User not found.");
            return null;
        }

        return user;
    }


    public Boolean registerUser(User user) {
        if (user == null ||
                user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return false;
        }

        usersRepository.save(user);
        return true;
    }
}
