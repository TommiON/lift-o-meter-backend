package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.*;
import org.tommi.back.repositories.WorkoutRepository;

import java.util.ArrayList;
import java.util.Date;

@Component
public class WorkoutFactory {
    private final double PROGRESS_FACTOR = 1.025;
    private final double DELOAD_FACTOR = 0.9;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private MoveSetFactory moveSetFactory;

    public Workout buildInitial(Cycle cycle, double squat, double bench, double row, double overhead, double deadlift) {
        Date date = null;
        Workout workout = new Workout(cycle, "A", new ArrayList<>(), date);

        ArrayList<MoveSet> moveSets = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", squat);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "BENCH", bench);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "ROW", row);
            moveSets.add(moveSet);
        }

        workout.setSets(moveSets);

        return workoutRepository.save(workout);
    }

}
