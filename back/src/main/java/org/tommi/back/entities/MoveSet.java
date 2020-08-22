package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoveSet {

    @NotNull
    @ManyToOne
    private Workout workout;

    @NotNull
    private Move move;

    @Size(min = 0)
    @Size(max = 5)
    private int repetitions;

}
