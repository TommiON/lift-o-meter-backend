package org.tommi.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.tommi.back.entities.Move;
import org.tommi.back.entities.Workout;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoveSet extends AbstractPersistable<Long> {

    @NotNull
    @ManyToOne
    private Workout workout;

    @NotNull
    private Move move;

    @Size(min = 0)
    @Size(max = 5)
    private int repetitions;

}
