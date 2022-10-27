package com.example.oauthservice.clients;

import com.example.usercommons.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/users/search/find-username")
    public User findByUsername(@RequestParam String name);
}
