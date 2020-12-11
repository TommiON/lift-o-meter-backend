package org.tommi.back.utils;

import org.springframework.beans.factory.annotation.Autowired;
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
        UserAccount account = userAccountRepository.findByUsername(username).get();
        return account;
    }
}