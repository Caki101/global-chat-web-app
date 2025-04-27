package com.study.global_chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Encryption {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Encryption(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public Boolean decrypt(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
