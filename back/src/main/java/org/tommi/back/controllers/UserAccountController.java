package org.tommi.back.controllers;

//import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.services.UserAccountService;

import java.util.ArrayList;

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    //TODO: Testailua vasta, pitää muuttaa POST-endpointiksi
    @GetMapping("/adduser")
    public void postNew() {
        try {
            userAccountService.addUser(new ArrayList<>(),
                    "Tommi",
                    true,
                    12,
                    40,
                    140,
                    20,
                    20,
                    20,
                    20,
                    20);
        } catch (Exception e) {
            System.out.println("Virhe!");
        }
        try {
            userAccountService.addUser(new ArrayList<>(),
                    "Hanna",
                    true,
                    47,
                    40,
                    140,
                    20,
                    20,
                    20,
                    20,
                    20);
        } catch (Exception e) {
            System.out.println("Virhe!" + e);
        }
    }

    @GetMapping("/users")
    public String getUsers() {
        String users = new String();
        try {
            users = userAccountService.getUsers();
        } catch (Exception e) {
            System.out.println("Virhe! " + e);
        }
        return users;
    }
}