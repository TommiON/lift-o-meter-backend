package org.tommi.back.domain;

import org.springframework.stereotype.Component;
import org.tommi.back.entities.MoveSet;
import org.tommi.back.entities.Workout;

import java.util.HashSet;

@Component
public class FailureChecker {

    public Workout checkAndUpdate(Workout workout) {
        HashSet<String> allMoves = new HashSet<>();
        HashSet<String> failedMoves = new HashSet<>();

        for (MoveSet move : workout.getSets()) {
            allMoves.add(move.getMove());
            if (move.getRepetitions() < 5) {
                failedMoves.add(move.getMove());
            }
        }

        for (String move : allMoves) {
            if (failedMoves.contains(move)) {
                switch (move) {
                    case "SQUAT":
                        workout.setSquatFailures(workout.getSquatFailures() + 1);
                        break;
                    case "BENCH":
                        workout.setBenchFailures(workout.getBenchFailures() + 1);
                        break;
                    case "ROW":
                        workout.setRowFailures(workout.getRowFailures() + 1);
                        break;
                    case "OVERHEAD":
                        workout.setOverheadFailures(workout.getOverheadFailures() + 1);
                        break;
                    case "DEADLIFT":
                        workout.setDeadliftFailures(workout.getDeadliftFailures() + 1);
                        break;
                }
            } else {
                switch (move) {
                    case "SQUAT":
                        workout.setSquatFailures(0);
                        break;
                    case "BENCH":
                        workout.setBenchFailures(0);
                        break;
                    case "ROW":
                        workout.setRowFailures(0);
                        break;
                    case "OVERHEAD":
                        workout.setOverheadFailures(0);
                        break;
                    case "DEADLIFT":
                        workout.setDeadliftFailures(0);
                        break;
                }
            }
        }

        return workout;
    }
}