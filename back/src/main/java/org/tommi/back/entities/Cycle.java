package org.tommi.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "cycle", cascade = CascadeType.ALL)
    private List<Workout> workouts;

    private Date startDate;
    private Date endDate;
    private boolean active;

    private double squatStartWeight;
    private double deadliftStartWeigth;
    private double benchPressStartWeight;
    private double barbellRowStartWeigth;
    private double overheadPressStartWeight;
}
