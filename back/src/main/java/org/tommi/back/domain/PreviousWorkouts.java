package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.WorkoutRepository;
import org.tommi.back.services.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class PreviousWorkouts {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private CurrentUser currentUser;

    private List<Workout> findPreviousWorkouts() {
        List<Workout> workouts = new ArrayList();

        UserAccount owner = currentUser.get();
        workouts = owner.getActiveCycle().getWorkouts();

        int numberOfWorkouts = Math.min(workouts.size(), 6);

        // kesken, pitää rakentaa logiikka että palauttaa 6 viimeistä

        return workouts;

    }
}
