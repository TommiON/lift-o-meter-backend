package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.payloads.MoveSetPayload;
import org.tommi.back.repositories.MoveSetRepository;

@CrossOrigin(origins = {"http://lift-o-meter-front.herokuapp.com", "https://lift-o-meter-front.herokuapp.com", "http://localhost:3000"})
@RestController
@RequestMapping("/api/moveset")
public class MoveSetController {

    @Autowired
    MoveSetRepository moveSetRepository;

    // epästabiili, pitää miettiä optionalit ja poikkeukset
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MoveSet updateSet(@RequestBody MoveSetPayload changedSet, @PathVariable Long id) {
        MoveSet moveSet = moveSetRepository.findById(id).get();
        int newReps = changedSet.getNewRepetitions();
        moveSet.setRepetitions(newReps);
        return moveSetRepository.save(moveSet);
    }
}
