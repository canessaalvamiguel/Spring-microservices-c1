package com.example.oauthservice.services;

import com.example.usercommons.models.User;

public interface IUserService {
    public User findByUsername(String username);
}
