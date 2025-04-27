package com.study.global_chat.controllers;

import com.study.global_chat.classes.Message;
import com.study.global_chat.classes.User;
import com.study.global_chat.services.Dbs;
import com.study.global_chat.services.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestfulController {
    private final Dbs dbs;
    private final Encryption encryption;

    @Autowired
    public RestfulController(Dbs dbs, Encryption encryption) {
        this.dbs = dbs;
        this.encryption = encryption;
    }

    @GetMapping("/messages")
    public List<Message> getMessages(){
        return dbs.getAllMessages();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return dbs.getAllUsers();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = dbs.getUser(username);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User db_user = dbs.getUser(user.getUsername());
        if (db_user == null) return ResponseEntity.status(404).body("User not found.");

        return encryption.decrypt(user.getPassword(), db_user.getPassword())? ResponseEntity.ok(db_user) : ResponseEntity.status(401).body("Incorrect password.");
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        user.setPassword(encryption.encrypt(user.getPassword()));
        return ResponseEntity.ok(dbs.registerUser(user)?"Success":"Failed registration");
    }
}
