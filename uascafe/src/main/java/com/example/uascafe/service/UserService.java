package com.example.uascafe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.uascafe.entity.User;
import com.example.uascafe.repository.UserRepository;
import com.example.uascafe.interfaces.Login;

@Service
public class UserService implements Login {

    @Autowired
    private UserRepository userRepository;

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
