package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.WorkoutRepository;
import org.tommi.back.services.CurrentUser;

import java.util.ArrayList;
import java.util.List;

@Component
public class PreviousWorkouts {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private CurrentUser currentUser;

    // muutetaan privateksi, nyt public testausta varten
    public List<Workout> findPreviousWorkouts() {
        UserAccount owner = currentUser.get();
        List<Workout> workouts = new ArrayList<>();
        workouts = owner.getActiveCycle().getWorkouts();

        int totalNumber = workouts.size();
        int numberToRetrieve = Math.min(totalNumber, 6);

        return workouts.subList(totalNumber - numberToRetrieve, totalNumber);
    }

    // muutetaan privateksi, nyt public testausta varten
    public Workout findTheLatestWorkout() {
        List<Workout> latestWorkouts = findPreviousWorkouts();
        return latestWorkouts.get(latestWorkouts.size() - 1);
    }

    public String findTypeOfLatestWorkout() {
        Workout latest = findTheLatestWorkout();
        return latest.getType();
    }
}
