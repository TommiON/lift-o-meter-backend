package org.tommi.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cycle extends AbstractPersistable<Long> {

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount owner;

    @OneToMany(mappedBy = "cycle")
    private List<Workout> workouts;

    private Date startDate;
    private Date endDate;
    private boolean active;

    private double squatStartWeight;
    private double deadliftStartWeigth;
    private double benchPressStartWeight;
    private double barbellRowStartWeigth;
    private double overheadPressStartWeight;

    /*
    public Cycle(
            UserAccount owner,
            double squatStartWeight,
            double deadliftStartWeigth,
            double benchPressStartWeight,
            double barbellRowStartWeigth,
            double overheadPressStartWeight
    ) {
        this.owner = owner;
        this.squatStartWeight = squatStartWeight;
        this.deadliftStartWeigth = deadliftStartWeigth;
        this.benchPressStartWeight = benchPressStartWeight;
        this.barbellRowStartWeigth = barbellRowStartWeigth;
        this.overheadPressStartWeight = overheadPressStartWeight;
        this.workouts = new ArrayList<>();
        this.startDate = new Date();
        this.endDate = null;
        this.active = true;
    }

     */
}
