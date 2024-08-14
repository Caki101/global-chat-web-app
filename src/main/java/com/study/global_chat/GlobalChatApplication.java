package com.study.global_chat;

import com.study.global_chat.classes.Message;
import com.study.global_chat.classes.User;
import com.study.global_chat.mongodb_communication.MessagesRepository;
import com.study.global_chat.mongodb_communication.MongoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class GlobalChatApplication implements CommandLineRunner {
	@Autowired
	private MongoServices mongoServices;

	public static void main(String[] args) {
		SpringApplication.run(GlobalChatApplication.class, args);
	}

	@GetMapping("/messages")
	public List<Message> getMessages(){
		return mongoServices.getMessages();
	}

	@GetMapping("/users")
	public List<User> getUsers() {
		return mongoServices.getUsers();
	}

	@GetMapping("/user/{username}")
	public List<User> getUser(@PathVariable String username) {
		User user = mongoServices.getUser(username);
		return user!=null?List.of(user):List.of(new User());
	}

	@PostMapping("/add_user")
	public void addUser(@RequestBody User user) {
		mongoServices.saveUser(user);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
