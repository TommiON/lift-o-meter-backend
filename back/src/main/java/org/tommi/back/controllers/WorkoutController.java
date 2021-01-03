package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.CycleRepository;
import org.tommi.back.repositories.MoveSetRepository;
import org.tommi.back.repositories.WorkoutRepository;
import org.tommi.back.services.CurrentUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private MoveSetRepository moveSetRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout getNextWorkout() {
        UserAccount owner = currentUser.get();
        Cycle currentCycle = cycleRepository.findByOwner(owner);

        List<Workout> workouts = new ArrayList<>();
        workouts = currentCycle.getWorkouts();

        return workouts.get(workouts.size() - 1);
    }

    @GetMapping(value = "/reset/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout reset(@PathVariable Long id) {
        Workout workout = workoutRepository.getOne(id);

        for (MoveSet m : workout.getSets()) {
            m.setRepetitions(-1);
        }
        workout.setDate(null);

        return workoutRepository.save(workout);
    }

}
