package org.tommi.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

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

    @NotNull
    @ManyToOne
    private Cycle cycle;

    // konstruktori hoitaa populoimisen, saa parametrina edellisen Workoutin ja päättelee siitä seuraavan?
    @OneToMany(mappedBy = "workout")
    private List<MoveSet> sets;

    @NotNull
    private Date date;

}
