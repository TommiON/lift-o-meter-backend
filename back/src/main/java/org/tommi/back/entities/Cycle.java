package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cycle extends AbstractPersistable<Long> {

    @NotNull
    @ManyToOne
    private UserAccount trainer;

    @OneToMany(mappedBy = "cycle")
    private ArrayList<Workout> workouts;

    @FutureOrPresent
    private Date startDate;

    @FutureOrPresent
    private Date endDate;

    @NotNull
    private boolean active;
}
