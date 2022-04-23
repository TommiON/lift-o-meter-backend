package org.tommi.back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;
import org.tommi.back.services.CurrentUser;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/users")
@RestController
public class UserAccountController {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CurrentUser currentUser;

    @GetMapping(value = "/current_user", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount getCurrentUser(Principal principal) {
        return currentUser.get();
    }

    // Ongelma: ei osaa tehdä json:aa kun cyclet mukana
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserAccount> getUsers() {
        List<UserAccount> users = new ArrayList<>();
        try {
            users = userAccountRepository.findAll();
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
        return users;
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        try {
            userAccountRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
    }
}