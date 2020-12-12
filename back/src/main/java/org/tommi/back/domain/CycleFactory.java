package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.entities.Workout;
import org.tommi.back.repositories.CycleRepository;
import org.tommi.back.utils.CurrentUser;
import org.tommi.back.utils.WeightRounder;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CycleFactory {

    @Autowired
    CycleRepository cycleRepository;

    public Cycle build(UserAccount owner, double squat, double deadlift, double bench, double row, double overhead) {

        double startSquat       = Math.max((WeightRounder.roundDownToNearest2_5(squat / 2)),    20);
        double startDeadlift    = Math.max((WeightRounder.roundDownToNearest2_5(deadlift / 2)), 20);
        double startBench       = Math.max((WeightRounder.roundDownToNearest2_5(bench / 2)),    20);
        double startRow         = Math.max((WeightRounder.roundDownToNearest2_5(row / 2)),      20);
        double startOverhead    = Math.max((WeightRounder.roundDownToNearest2_5(overhead / 2)), 20);

        Date startDate = new Date();
        Date endDate = null;
        boolean active = true;

        Cycle cycle = new Cycle(
                owner,
                new ArrayList<>(),
                startDate,
                endDate,
                active,
                startSquat,
                startDeadlift,
                startBench,
                startRow,
                startOverhead);

        cycleRepository.save(cycle);

        return cycle;
    }
}
