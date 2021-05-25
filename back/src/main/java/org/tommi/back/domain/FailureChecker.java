package org.tommi.back.domain;

import org.springframework.stereotype.Component;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.entities.Workout;

import java.util.HashSet;

@Component
public class FailureChecker {

    public Workout checkAndUpdate(Workout workout) {
        HashSet<String> checkedMoves = new HashSet<>();

        for(MoveSet move : workout.getSets()) {
            if(move.getRepetitions() < 5) {
                if(!checkedMoves.contains(move.getMove())) {
                    checkedMoves.add(move.getMove());
                    switch (move.getMove()) {
                        case "SQUAT":
                            workout.setSquatFailures(workout.getSquatFailures() + 1);
                            workout.setLatestSquatFailed(true);
                            break;
                        case "BENCH":
                            workout.setBenchFailures(workout.getBenchFailures() + 1);
                            workout.setLatestBenchFailed(true);
                            break;
                        case "ROW":
                            workout.setRowFailures(workout.getRowFailures() + 1);
                            workout.setLatestRowFailed(true);
                            break;
                        case "OVERHEAD":
                            workout.setOverheadFailures(workout.getOverheadFailures() + 1);
                            workout.setLatestOverheadFailed(true);
                            break;
                        case "DEADLIFT":
                            workout.setDeadliftFailures(workout.getDeadliftFailures() + 1);
                            workout.setLatestDeadliftFailed(true);
                            break;
                    }
                }
            }
        }

        return workout;
    }
}
