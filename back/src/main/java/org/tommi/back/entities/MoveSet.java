package org.tommi.back.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.tommi.back.entities.Move;
import org.tommi.back.entities.Workout;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MoveSet extends AbstractPersistable<Long> {

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    private Workout workout;

    private String move;

    private double weigth;

    private int repetitions;
}
