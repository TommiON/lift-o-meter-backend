package org.tommi.back.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;

@Component
public class CurrentUser {

    @Autowired
    UserAccountRepository userAccountRepository;

    public UserAccount get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("*** CurrentUser: " + username);
        UserAccount account = userAccountRepository.findByUsername(username).get();
        return account;
    }
}