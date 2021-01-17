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

    // tästä kannattaisi refaktoroitua eroon ja hoitaa kaikki buildNext():lla
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

    public Workout buildNext(Cycle cycle) {
        Date date = null;
        String type = inferType();
        Workout workout = new Workout(cycle, type, new ArrayList<>(), date);

        ArrayList<MoveSet> moveSets;
        if(cycle.getWorkouts().size() == 1) {
            moveSets = buildInitialForB(workout);
        } else if(type.equals("A")) {
            moveSets = generateMoveSetsForA(workout);
        } else {
            moveSets = generateMoveSetsForB(workout);
        }

        workout.setSets(moveSets);

        return workoutRepository.save(workout);
    }

    private String inferType() {
        if(previousWorkouts.findTypeOfLatestWorkout().equals("A")) {
            return "B";
        } else {
            return "A";
        }
    }

    private ArrayList<MoveSet> generateMoveSetsForA(Workout workout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeigth = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeigth);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "SQUAT", newSquatWeigth);
            moveSets.add(moveSet);
        }

        double previousBenchWeight = previousWorkouts.getLatestWeightsFor("BENCH");
        double newBenchWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousBenchWeight);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "BENCH", newBenchWeight);
            moveSets.add(moveSet);
        }

        double previousRowWeight = previousWorkouts.getLatestWeightsFor("ROW");
        double newRowWeight = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousRowWeight);
        for(int i = 1; i <= 5; i++) {
            MoveSet moveSet = moveSetFactory.build(workout, "ROW", newRowWeight);
            moveSets.add(moveSet);
        }

        return moveSets;
    }

    private ArrayList<MoveSet> generateMoveSetsForB(Workout workout) {
        ArrayList<MoveSet> moveSets = new ArrayList<>();

        double previousSquatWeight = previousWorkouts.getLatestWeightsFor("SQUAT");
        double newSquatWeigth = WeightRounder.roundUpToNearest2_5(PROGRESS_FACTOR * previousSquatWeight);
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
}
