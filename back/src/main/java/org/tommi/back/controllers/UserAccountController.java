package org.tommi.back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.services.UserAccountService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserAccount> getUsers() {
        List<UserAccount> users = new ArrayList<>();
        try {
            users = userAccountService.getUsers();
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
        return users;
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount addUser(@RequestBody String jsonInput) {
        try {
            UserAccount newUser = mapper.readValue(jsonInput, UserAccount.class);
            return userAccountService.createUser(newUser);
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
        return null;
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        try {
            userAccountService.deleteUser(id);
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
    }
}