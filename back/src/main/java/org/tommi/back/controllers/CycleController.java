package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.CycleRepository;
import org.tommi.back.services.CurrentUser;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/cycle")
@RestController
public class CycleController {

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CurrentUser currentUser;

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cycle getCurrent() {
        UserAccount owner = currentUser.get();
        return cycleRepository.findByOwner(owner);
    }
}
