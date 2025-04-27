package com.study.global_chat.postgresql_comms;

import com.study.global_chat.classes.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Message, Long> {
}
