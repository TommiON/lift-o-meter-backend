package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.repositories.MoveSetRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/moveset")
public class MoveSetController {

    @Autowired
    MoveSetRepository moveSetRepository;

    // epästabiili, pitää miettiä optionalit ja poikkeukset
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MoveSet updateSet(@RequestBody int newReps, @PathVariable Long id) {
        MoveSet moveSet = moveSetRepository.findById(id).get();
        moveSet.setRepetitions(newReps);
        return moveSetRepository.save(moveSet);
    }
}
