package com.example.oauthservice.services;

import com.example.usercommons.models.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IUserService {
    public User findByUsername(String username);
    public User updateUser(User user, Long id);
}
