package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.*;
import org.tommi.back.repositories.WorkoutRepository;
import org.tommi.back.services.CurrentUser;
import org.tommi.back.services.WeightRounder;

import java.util.ArrayList;
import java.util.Date;

@Component
public class WorkoutFactory {
    private final double PROGRESS_FACTOR = 1.025;
    private final double DEADLIFT_PROGRESS_FACTOR = 1.05;
    private final double DELOAD_FACTOR = 0.9;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private MoveSetFactory moveSetFactory;

    @Autowired
    private PreviousWorkouts previousWorkouts;

    @Autowired
    private CurrentUser currentUser;

    public Workout buildInitial(Cycle cycle, double squat, double bench, double row, double overhead, double deadlift) {
        Date date    = null;
        Workout workout = new Workout(cycle, "A", new ArrayList<>(), date, 0, 0, 0, 0, 0);

        ArrayList<MoveSet> moveSets = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", squat, false);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "BENCH", bench, false);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "ROW", row,  false);
            moveSets.add(moveSet);
        }

        workout.setSets(moveSets);

        return workoutRepository.save(workout);
    }

    public Workout buildNext(Cycle cycle, Workout completedWorkout) {
        String type = inferType(completedWorkout);

        Workout nextWorkout = new Workout(cycle,
                type,
                new ArrayList<>(),
                null,
                completedWorkout.getCumulativeFailedSquat(),
                completedWorkout.getCumulativeFailedBenchPress(),
                completedWorkout.getCumulativeFailedBarbellRow(),
                completedWorkout.getCumulativeFailedOverheadPress(),
                completedWorkout.getCumulativeFailedDeadlift());

        ArrayList<MoveSet> moveSets;

        if(cycle.getWorkouts().size() == 1) {
            moveSets = buildInitialForB(nextWorkout);
        } else if(type.equals("A")) {
            moveSets = generateMoveSetsForA(nextWorkout, completedWorkout);
        } else {
            moveSets = generateMoveSetsForB(nextWorkout);
        }

        nextWorkout.setSets(moveSets);

        return workoutRepository.save(nextWorkout);
    }

    private String inferType(Workout previousWorkout) {
        if(previousWorkout.getType().equals("A")) {
            return "B";
        } else {
            return "A";
        }
    }

    private ArrayList<MoveSet> generateMoveSetsForA(Workout nextWorkout, Workout completedWorkout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeigth = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth;
        boolean squatDeloaded = false;
        if(completedWorkout.getCumulativeFailedSquat() == 1 || completedWorkout.getCumulativeFailedSquat() == 2) {
            newSquatWeigth = previousSquatWeigth;
        } else if(completedWorkout.getCumulativeFailedSquat() == 3) {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousSquatWeigth);
            squatDeloaded = true;
        } else {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeigth);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "SQUAT", newSquatWeigth, squatDeloaded);
            moveSets.add(moveSet);
        }

        double previousBenchWeight = previousWorkouts.getLatestWeightsFor("BENCH");
        double newBenchWeight;
        boolean benchDeloaded = false;
        if(completedWorkout.getCumulativeFailedBenchPress() == 1 || completedWorkout.getCumulativeFailedBenchPress() == 2) {
            newBenchWeight = previousBenchWeight;
        } else if(completedWorkout.getCumulativeFailedBenchPress() == 3) {
            newBenchWeight = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousBenchWeight);
            benchDeloaded = true;
        } else {
            newBenchWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousBenchWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "BENCH", newBenchWeight, benchDeloaded);
            moveSets.add(moveSet);
        }

        double previousRowWeight = previousWorkouts.getLatestWeightsFor("ROW");
        double newRowWeight;
        boolean rowDeloaded = false;
        if(completedWorkout.getCumulativeFailedBarbellRow() == 1 || completedWorkout.getCumulativeFailedBarbellRow() == 2) {
            newRowWeight = previousRowWeight;
        } else if(completedWorkout.getCumulativeFailedBarbellRow() == 3) {
            newRowWeight = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousRowWeight);
            rowDeloaded = true;
        } else {
            newRowWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousRowWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "ROW", newRowWeight, false);
            moveSets.add(moveSet);
        }

        return moveSets;
    }

    private ArrayList<MoveSet> generateMoveSetsForB(Workout workout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeight = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth;
        boolean squatDeloaded = false;
        if(previousWorkouts.oneOrTwoFailuresFor("SQUAT")) {
            newSquatWeigth = previousSquatWeight;
        } else if(previousWorkouts.threeFailuresFor("SQUAT")) {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousSquatWeight);
            squatDeloaded = true;
        } else {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", newSquatWeigth, squatDeloaded);
            moveSets.add(moveSet);
        }

        double previousOverheadWeight = previousWorkouts.getLatestWeightsFor("OVERHEAD");
        double newOverheadWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousOverheadWeight);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "OVERHEAD", newOverheadWeight, false);
            moveSets.add(moveSet);
        }

        double previousDeadliftWeight = previousWorkouts.getLatestWeightsFor("DEADLIFT");
        double newDeadliftWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousDeadliftWeight);
        MoveSet moveSet = moveSetFactory.build(workout, "DEADLIFT", newDeadliftWeight, false);
        moveSets.add(moveSet);

        return moveSets;
    }

    private ArrayList<MoveSet> buildInitialForB(Workout workout) {
        Cycle cycle = currentUser.get().getActiveCycle();
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeigth = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeigth);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", newSquatWeigth, false);
            moveSets.add(moveSet);
        }

        double initialOverheadWeight = cycle.getOverheadPressStartWeight();
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "OVERHEAD", initialOverheadWeight, false);
            moveSets.add(moveSet);
        }

        double initialDeadliftWeight = cycle.getDeadliftStartWeigth();
        MoveSet moveSet = moveSetFactory.build(workout, "DEADLIFT", initialDeadliftWeight, false);
        moveSets.add(moveSet);

        return moveSets;
    }
}
