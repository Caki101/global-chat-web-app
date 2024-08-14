package com.study.global_chat.mongodb_communication;

import com.study.global_chat.classes.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessagesRepository extends MongoRepository<Message, String> {
}
