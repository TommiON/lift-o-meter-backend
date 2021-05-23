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
                            workout.setCumulativeFailedSquat(workout.getCumulativeFailedSquat() + 1);
                            break;
                        case "BENCH":
                            workout.setCumulativeFailedBenchPress(workout.getCumulativeFailedBenchPress() + 1);
                            break;
                        case "ROW":
                            workout.setCumulativeFailedBarbellRow(workout.getCumulativeFailedBarbellRow() + 1);
                            break;
                        case "OVERHEAD":
                            workout.setCumulativeFailedOverheadPress(workout.getCumulativeFailedOverheadPress() + 1);
                            break;
                        case "DEADLIFT":
                            workout.setCumulativeFailedDeadlift(workout.getCumulativeFailedDeadlift() + 1);
                    }
                }
            }
        }

        return workout;
    }
}
