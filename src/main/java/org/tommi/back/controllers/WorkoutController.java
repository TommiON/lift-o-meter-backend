package org.tommi.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tommi.back.domain.FailureChecker;
import org.tommi.back.domain.PreviousWorkouts;
import org.tommi.back.domain.WorkoutFactory;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.CycleRepository;
import org.tommi.back.repositories.MoveSetRepository;
import org.tommi.back.repositories.WorkoutRepository;
import org.tommi.back.services.CurrentUser;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    // väliaikainen
    @Autowired
    private PreviousWorkouts previousWorkouts;
    // poistetaan, ei tarvita lopullisessa

    @Autowired
    private CurrentUser currentUser;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private MoveSetRepository moveSetRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutFactory workoutFactory;

    @Autowired
    private FailureChecker failureChecker;

    @GetMapping(value = "/next", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout getNextWorkout() {
        UserAccount owner = currentUser.get();
        Cycle currentCycle = cycleRepository.findByOwner(owner);

        List<Workout> workouts = new ArrayList<>();
        workouts = currentCycle.getWorkouts();

        return workouts.get(workouts.size() - 1);
    }

    @GetMapping(value = "/start/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout start(@PathVariable Long id) {
        Workout workout = workoutRepository.getOne(id);
        workout.setDate(new Date());

        return workoutRepository.save(workout);
    }

    @GetMapping(value = "/finish/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout finish(@PathVariable Long id) {
        UserAccount owner = currentUser.get();
        Cycle currentCycle = cycleRepository.findByOwner(owner);

        //Workout completedWorkout = workoutRepository.getOne(id);

        Workout completedWorkout = workoutRepository.findById(id).get();
        completedWorkout = failureChecker.checkAndUpdate(completedWorkout);
        workoutRepository.save(completedWorkout);

        Workout nextWorkout = workoutFactory.buildNext(currentCycle, completedWorkout);

        return nextWorkout;
    }

    @GetMapping(value = "/reset/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Workout reset(@PathVariable Long id) {
        Workout workout = workoutRepository.findById(id).get();

        for (MoveSet m : workout.getSets()) {
            m.setRepetitions(-1);
        }
        workout.setDate(null);

        return workoutRepository.save(workout);
    }

    @GetMapping(value = "/completed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Workout> getCompleted() {
        UserAccount owner = currentUser.get();
        Cycle currentCycle = cycleRepository.findByOwner(owner);

        List<Workout> workouts = currentCycle.getWorkouts();
        Collections.sort(workouts);
        Collections.reverse(workouts);

        return workouts;
    }

    // tämä testaamista varten, poistetaan myöhemmin!
    @GetMapping(value = "/previous", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Workout> getPreviousWorkouts() {
        return previousWorkouts.findPreviousWorkouts();
    }
    // poistetaan, ei tarvita lopullisessa

    // tämä testaamista varten, poistetaan myöhemmin!
    @GetMapping(value = "/previous/type", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTypeOfLatest() {
        return previousWorkouts.findTypeOfLatestWorkout();
    }
    // poistetaan, ei tarvita lopullisessa
}
