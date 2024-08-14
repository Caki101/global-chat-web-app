package com.study.global_chat.mongodb_communication;

import com.study.global_chat.classes.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
    User findUserByUsername(String username);
}
