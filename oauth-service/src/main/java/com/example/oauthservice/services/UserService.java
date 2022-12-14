package com.example.oauthservice.services;

import brave.Tracer;
import com.example.oauthservice.clients.UserFeignClient;
import com.example.usercommons.models.User;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService , IUserService{

    private final UserFeignClient client;
    private final Tracer tracer;
    private Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserFeignClient client, Tracer tracer) {
        this.client = client;
        this.tracer = tracer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            User user = client.findByUsername(username);

            List<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .peek(authority -> log.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            log.info("User " + username + " authenticated successfully");

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    authorities
            );
        }catch (FeignException e){
            String value = "Login error. User "+username+" not found.";

            log.error("Login error. User " + username + " not found.");
            tracer.currentSpan().tag("error.message", value + ": "+e.getMessage());

            throw new UsernameNotFoundException(value);
        }
    }

    @Override
    public User findByUsername(String username) {
        return client.findByUsername(username);
    }

    @Override
    public User updateUser(User user, Long id) {
        return client.updateUser(user, id);
    }
}
