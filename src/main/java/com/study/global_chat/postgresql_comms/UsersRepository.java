package com.study.global_chat.postgresql_comms;

import com.study.global_chat.classes.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
