package org.tommi.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Workout extends AbstractPersistable<Long> implements Comparable<Workout>{

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Cycle cycle;

    String type;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<MoveSet> sets;

    private Date date;

    private double targetSquat;
    private double targetBench;
    private double targetRow;
    private double targetOverhead;
    private double targetDeadlift;

    @JsonIgnore
    private int squatFailures;
    @JsonIgnore
    private int benchFailures;
    @JsonIgnore
    private int rowFailures;
    @JsonIgnore
    private int overheadFailures;
    @JsonIgnore
    private int deadliftFailures;

    @JsonIgnore
    private boolean latestSquatFailed;
    @JsonIgnore
    private boolean latestBenchFailed;
    @JsonIgnore
    private boolean latestRowFailed;
    @JsonIgnore
    private boolean latestOverheadFailed;
    @JsonIgnore
    private boolean latestDeadliftFailed;

    @Override
    public int compareTo(Workout w) {
       if(this.date == null || w.date == null) {
           return 0;
       }

       if(this.date.before(w.date)) {
           return -1;
       } else {
           return 1;
       }
    }
}
