package com.example.oauthservice.security.event;

import com.example.oauthservice.services.IUserService;
import com.example.usercommons.models.User;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    private final IUserService userService;
    private static final Integer MAX_LOGGIN_ATEMPTS = 3;

    public AuthenticationSuccessErrorHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        if(authentication.getDetails() instanceof WebAuthenticationDetails)
            return;

        UserDetails logginUser = (UserDetails) authentication.getPrincipal();
        log.info("Success login for user "+logginUser);

        User user = userService.findByUsername(authentication.getName());
        if(user.getLoginAttempts() != null && user.getLoginAttempts() > 0){
            user.setLoginAttempts(0);
            userService.updateUser(user, user.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        log.error("Failed to login user. Error: "+e.getMessage());

        try{
            User user = userService.findByUsername(authentication.getName());
            if(user.getLoginAttempts() == null)
                user.setLoginAttempts(0);

            user.setLoginAttempts(user.getLoginAttempts() + 1);
            log.info(String.format("User %s current login attempts %s", user.getUsername(), user.getLoginAttempts()));

            if(user.getLoginAttempts() >= MAX_LOGGIN_ATEMPTS){
                log.error(String.format("User %s was disabled due to max logging attempts reached", user.getUsername()));
                user.setEnabled(false);
            }

            userService.updateUser(user, user.getId());

        }catch (FeignException exception){
            log.error(String.format("User %s not found", authentication.getName()));
        }

    }
}
