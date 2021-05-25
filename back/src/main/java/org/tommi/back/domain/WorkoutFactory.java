package org.tommi.back.domain;

import org.hibernate.jdbc.Work;
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

    public Workout buildInitial(Cycle cycle, double squatStart, double benchStart, double rowStart, double overheadStart, double deadliftStart) {
        Date date = null;
        Workout workout = new Workout(
                cycle,
                "A",
                new ArrayList<>(),
                date,
                squatStart, benchStart, rowStart, overheadStart, deadliftStart,
                0, 0, 0, 0, 0,
                false, false, false, false, false);

        ArrayList<MoveSet> moveSets = new ArrayList<>();
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", squatStart);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "BENCH", benchStart);
            moveSets.add(moveSet);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "ROW", rowStart);
            moveSets.add(moveSet);
        }

        workout.setSets(moveSets);

        return workoutRepository.save(workout);
    }

    public Workout buildNext(Cycle cycle, Workout completedWorkout) {
        String type = inferType(completedWorkout);

        Workout nextWorkout = new Workout(
                cycle,
                type,
                new ArrayList<>(),
                null,
                completedWorkout.getTargetSquat(),
                completedWorkout.getTargetBench(),
                completedWorkout.getTargetRow(),
                completedWorkout.getTargetOverhead(),
                completedWorkout.getTargetDeadlift(),
                completedWorkout.getSquatFailures(),
                completedWorkout.getBenchFailures(),
                completedWorkout.getRowFailures(),
                completedWorkout.getOverheadFailures(),
                completedWorkout.getDeadliftFailures(),
                false, false, false, false, false
                );

        if(cycle.getWorkouts().size() == 1) {
            nextWorkout = decorateWithInitialSetsForB(nextWorkout, completedWorkout);
        } else if(type.equals("A")) {
            nextWorkout = decorateWithSetsForA(nextWorkout, completedWorkout);
        } else {
            nextWorkout = decorateWithSetsForB(nextWorkout, completedWorkout);
        }
        /*
        ArrayList<MoveSet> moveSets;

        if(cycle.getWorkouts().size() == 1) {
            moveSets = buildInitialForB(nextWorkout);
        } else if(type.equals("A")) {
            moveSets = generateMoveSetsForA(nextWorkout, completedWorkout);
        } else {
            moveSets = generateMoveSetsForB(nextWorkout);
        }

        nextWorkout.setSets(moveSets);
        */

        return workoutRepository.save(nextWorkout);
    }

    private Workout decorateWithSetsForA(Workout nextWorkout, Workout completedWorkout) {
        Workout underConstruction = nextWorkout;
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        if(completedWorkout.isLatestSquatFailed()) {
            if(completedWorkout.getSquatFailures() == 1 || completedWorkout.getSquatFailures() == 2) {
                underConstruction.setTargetSquat(completedWorkout.getTargetSquat());
            } else if(completedWorkout.getSquatFailures() == 3) {
                underConstruction.setTargetSquat(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetSquat()));
                underConstruction.setSquatFailures(0);
            }
        } else {
            underConstruction.setTargetSquat(WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetSquat()));
            underConstruction.setSquatFailures(0);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "SQUAT", underConstruction.getTargetSquat());
            moveSets.add(m);
        }

        if(completedWorkout.isLatestBenchFailed()) {
            if(completedWorkout.getBenchFailures() == 1 || completedWorkout.getBenchFailures() == 2) {
                underConstruction.setTargetBench(completedWorkout.getTargetBench());
            } else if(completedWorkout.getBenchFailures() == 3) {
                underConstruction.setTargetBench(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetBench()));
                underConstruction.setBenchFailures(0);
            }
        } else {
            underConstruction.setTargetBench(WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetSquat()));
            underConstruction.setBenchFailures(0);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "BENCH", underConstruction.getTargetBench());
            moveSets.add(m);
        }

        if(completedWorkout.isLatestRowFailed()) {
            if(completedWorkout.getRowFailures() == 1 || completedWorkout.getRowFailures() == 2) {
                underConstruction.setTargetRow(completedWorkout.getTargetRow());
            } else if(completedWorkout.getRowFailures() == 3) {
                underConstruction.setTargetRow(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetRow()));
                underConstruction.setRowFailures(0);
            }
        } else {
            underConstruction.setTargetRow(WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetRow()));
            underConstruction.setRowFailures(0);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "ROW", underConstruction.getTargetRow());
            moveSets.add(m);
        }

        underConstruction.setSets(moveSets);

        return underConstruction;
    }

    private Workout decorateWithSetsForB(Workout nextWorkout, Workout completedWorkout) {
        Workout underConstruction = nextWorkout;
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        if(completedWorkout.isLatestSquatFailed()) {
            if(completedWorkout.getSquatFailures() == 1 || completedWorkout.getSquatFailures() == 2) {
                underConstruction.setTargetSquat(completedWorkout.getTargetSquat());
            } else if(completedWorkout.getSquatFailures() == 3) {
                underConstruction.setTargetSquat(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetSquat()));
                underConstruction.setSquatFailures(0);
            }
        } else {
            underConstruction.setTargetSquat(WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetSquat()));
            underConstruction.setSquatFailures(0);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "SQUAT", underConstruction.getTargetSquat());
            moveSets.add(m);
        }

        if(completedWorkout.isLatestOverheadFailed()) {
            if(completedWorkout.getOverheadFailures() == 1 || completedWorkout.getOverheadFailures() == 2) {
                underConstruction.setTargetOverhead(completedWorkout.getTargetOverhead());
            } else if(completedWorkout.getOverheadFailures() == 3) {
                underConstruction.setTargetOverhead(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetOverhead()));
                underConstruction.setOverheadFailures(0);
            }
        } else {
            underConstruction.setTargetOverhead((WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetOverhead())));
            underConstruction.setOverheadFailures(0);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "OVERHEAD", underConstruction.getTargetOverhead());
            moveSets.add(m);
        }

        if(completedWorkout.isLatestDeadliftFailed()) {
            if(completedWorkout.getDeadliftFailures() == 1 || completedWorkout.getDeadliftFailures() == 2) {
                underConstruction.setTargetDeadlift(completedWorkout.getTargetDeadlift());
            } else if(completedWorkout.getDeadliftFailures() == 3) {
                underConstruction.setTargetDeadlift(WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * completedWorkout.getTargetDeadlift()));
                underConstruction.setDeadliftFailures(0);
            }
        } else {
            underConstruction.setTargetDeadlift(WeightRounder.roundUpToNearest2_5(DEADLIFT_PROGRESS_FACTOR * completedWorkout.getTargetDeadlift()));
            underConstruction.setDeadliftFailures(0);
        }
        MoveSet m = moveSetFactory.build(underConstruction, "DEADLIFT", underConstruction.getTargetDeadlift());
        moveSets.add(m);

        underConstruction.setSets(moveSets);

        return underConstruction;
    }

    private String inferType(Workout previousWorkout) {
        if(previousWorkout.getType().equals("A")) {
            return "B";
        } else {
            return "A";
        }
    }

    /*
    private ArrayList<MoveSet> generateMoveSetsForA(Workout nextWorkout, Workout completedWorkout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeigth = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth;
        if(completedWorkout.getSquatFailures() == 1 || completedWorkout.getSquatFailures() == 2) {
            newSquatWeigth = previousSquatWeigth;
        } else if(completedWorkout.getSquatFailures() == 3) {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousSquatWeigth);
        } else {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeigth);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "SQUAT", newSquatWeigth);
            moveSets.add(moveSet);
        }

        double previousBenchWeight = previousWorkouts.getLatestWeightsFor("BENCH");
        double newBenchWeight;
        if(completedWorkout.getBenchFailures() == 1 || completedWorkout.getBenchFailures() == 2) {
            newBenchWeight = previousBenchWeight;
        } else if(completedWorkout.getBenchFailures() == 3) {
            newBenchWeight = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousBenchWeight);
        } else {
            newBenchWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousBenchWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "BENCH", newBenchWeight);
            moveSets.add(moveSet);
        }

        double previousRowWeight = previousWorkouts.getLatestWeightsFor("ROW");
        double newRowWeight;
        if(completedWorkout.getRowFailures() == 1 || completedWorkout.getRowFailures() == 2) {
            newRowWeight = previousRowWeight;
        } else if(completedWorkout.getRowFailures() == 3) {
            newRowWeight = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousRowWeight);
        } else {
            newRowWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousRowWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(nextWorkout, "ROW", newRowWeight);
            moveSets.add(moveSet);
        }

        return moveSets;
    }

    private ArrayList<MoveSet> generateMoveSetsForB(Workout workout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeight = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth;
        if(previousWorkouts.oneOrTwoFailuresFor("SQUAT")) {
            newSquatWeigth = previousSquatWeight;
        } else if(previousWorkouts.threeFailuresFor("SQUAT")) {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(DELOAD_FACTOR * previousSquatWeight);
        } else {
            newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeight);
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", newSquatWeigth);
            moveSets.add(moveSet);
        }

        double previousOverheadWeight = previousWorkouts.getLatestWeightsFor("OVERHEAD");
        double newOverheadWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousOverheadWeight);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "OVERHEAD", newOverheadWeight);
            moveSets.add(moveSet);
        }

        double previousDeadliftWeight = previousWorkouts.getLatestWeightsFor("DEADLIFT");
        double newDeadliftWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousDeadliftWeight);
        MoveSet moveSet = moveSetFactory.build(workout, "DEADLIFT", newDeadliftWeight);
        moveSets.add(moveSet);

        return moveSets;
    }

    private ArrayList<MoveSet> buildInitialForB(Workout workout) {
        Cycle cycle = currentUser.get().getActiveCycle();
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeigth = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeigth);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", newSquatWeigth);
            moveSets.add(moveSet);
        }

        double initialOverheadWeight = cycle.getOverheadPressStartWeight();
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "OVERHEAD", initialOverheadWeight);
            moveSets.add(moveSet);
        }

        double initialDeadliftWeight = cycle.getDeadliftStartWeigth();
        MoveSet moveSet = moveSetFactory.build(workout, "DEADLIFT", initialDeadliftWeight);
        moveSets.add(moveSet);

        return moveSets;
    }

     */

    private Workout decorateWithInitialSetsForB(Workout nextWorkout, Workout completedWorkout) {
        Workout underConstruction = nextWorkout;
        Cycle cycle = currentUser.get().getActiveCycle();
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        if(completedWorkout.isLatestSquatFailed()) {
            underConstruction.setTargetSquat(completedWorkout.getTargetSquat());
        } else {
            underConstruction.setTargetSquat(WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * completedWorkout.getTargetSquat()));
        }
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "SQUAT", underConstruction.getTargetSquat());
            moveSets.add(m);
        }

        underConstruction.setTargetOverhead(cycle.getOverheadPressStartWeight());
        for(int i = 1; i <= 5; i++) {
            MoveSet m = moveSetFactory.build(underConstruction, "OVERHEAD", cycle.getOverheadPressStartWeight());
            moveSets.add(m);
        }

        underConstruction.setTargetDeadlift(cycle.getDeadliftStartWeigth());
        MoveSet m = moveSetFactory.build(underConstruction, "DEADLIFT", cycle.getDeadliftStartWeigth());
        moveSets.add(m);

        underConstruction.setSets(moveSets);

        return underConstruction;
    }
}
