package org.tommi.back.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;
import org.tommi.back.utils.CurrentUser;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
@RestController
public class UserAccountController {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CurrentUser currentUser;

    // Muunna siten että palauttaa UserAccountin
    @GetMapping("/current_user")
    public String getCurrentUser(Principal principal) {
        UserAccount current = currentUser.get();
        return current.getUsername();
    }

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