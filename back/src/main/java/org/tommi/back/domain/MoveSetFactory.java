package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.Move;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.MoveSetRepository;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class MoveSetFactory {

    @Autowired
    private MoveSetRepository moveSetRepository;

    public MoveSet build(Workout workout, String move, double weigth, boolean deloaded) {
        MoveSet moveSet = new MoveSet(workout, move, weigth, -1, deloaded);

        return moveSetRepository.save(moveSet);
    }
}
