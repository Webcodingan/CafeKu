package com.example.uascafe.interfaces;

import com.example.uascafe.entity.User;

public interface Login {
    User login(String username, String password);
}
