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
public class Workout extends AbstractPersistable<Long> {

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Cycle cycle;

    String type;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<MoveSet> sets;

    private Date date;

    private int cumulativeFailedSquat;
    private int cumulativeFailedBenchPress;
    private int cumulativeFailedBarbellRow;
    private int cumulativeFailedOverheadPress;
    private int cumulativeFailedDeadlift;
}
