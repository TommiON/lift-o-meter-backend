package org.tommi.back.domain;

import org.tommi.back.utils.WeightRounder;

public class CycleGenerator {

    public static void generate(double squat, double deadlift, double bench, double row, double overhead) {

        double startSquat = Math.max((WeightRounder.roundDownToNearest2_5(squat / 2)), 20);
        double startDeadlift = Math.max((WeightRounder.roundDownToNearest2_5(deadlift / 2)), 20);
        double startBench = Math.max((WeightRounder.roundDownToNearest2_5(bench / 2)), 20);
        double startRow = Math.max((WeightRounder.roundDownToNearest2_5(row / 2)), 20);
        double startOverhead = Math.max((WeightRounder.roundDownToNearest2_5(overhead / 2)), 20);

    }
}
