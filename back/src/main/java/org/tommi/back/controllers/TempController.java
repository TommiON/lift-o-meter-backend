package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;
import org.tommi.back.services.UserAccountService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TempController {

    @GetMapping("*")
    public String temp() {
        return "Elossa ollaan.";
    }
}
