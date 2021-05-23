package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.MoveSet;
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

    public String findTypeOfLatestWorkout() {
        Workout latest = findTheLatestWorkout();
        return latest.getType();
    }

    public double getLatestWeightsFor(String move) {
        // käytetään kyykylle
        Workout latest = findTheLatestWorkout();
        for(MoveSet m : latest.getSets()) {
            if(m.getMove().equals(move)) {
                return m.getWeigth();
            }
        }

        // käytetään muille
        if(findTheSecondLatestWorkout() != null) {
            Workout secondLatest = findTheSecondLatestWorkout();
            for(MoveSet m : secondLatest.getSets()) {
                if(m.getMove().equals(move)) {
                    return m.getWeigth();
                }
            }
        }

        return -100;
    }

    public boolean oneOrTwoFailuresFor(String move) {
        if(countFailuresFor(move) == 1 || countFailuresFor(move) == 2) {
            return true;
        }
        return false;
    }

    public boolean threeFailuresFor(String move) {
        if(countFailuresFor(move) >= 3) {
            return true;
        }
        return false;
    }

    // privaatit apumetodit -->

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

    private Workout findTheSecondLatestWorkout() {
        List<Workout> latestWorkouts = findPreviousWorkouts();
        try {
            return latestWorkouts.get(latestWorkouts.size() - 2);
        } catch (Exception e) {
            return null;
        }
    }

    private int countFailuresFor(String move) {
        Workout latestWorkout = findTheLatestWorkout();
        for(MoveSet moveSet : latestWorkout.getSets()) {
            if(moveSet.getMove().equals(move) && moveSet.isDeloaded()) {
                return 0;
            }
        }

        int failures = 0;
        List<Workout> latestWorkouts = findPreviousWorkouts();

        if(!move.equals("DEADLIFT")) {
            int numberToCheck = Math.min(3, latestWorkouts.size());
            latestWorkouts = latestWorkouts.subList(numberToCheck, latestWorkouts.size());
        }

        for(Workout workout : latestWorkouts) {
            for(MoveSet moveSet : workout.getSets()) {
                if(moveSet.getMove().equals(move) && moveSet.getRepetitions() < 5) {
                    failures++;
                    if(moveSet.isDeloaded()) {
                        return failures;
                    }
                    break;
                }
            }
        }

        return failures;
    }

}
