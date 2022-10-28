package com.example.oauthservice.clients;

import com.example.usercommons.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/users/search/find-username")
    public User findByUsername(@RequestParam String name);

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id);
}
