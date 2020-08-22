package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Workout {

    @NotNull
    @ManyToOne
    private Cycle cycle;

    // konstruktori hoitaa populoimisen, saa parametrina edellisen Workoutin ja päättelee siitä seuraavan?
    @OneToMany(mappedBy = "workout")
    private ArrayList<MoveSet> sets;

    @NotNull
    private Date date;

}
