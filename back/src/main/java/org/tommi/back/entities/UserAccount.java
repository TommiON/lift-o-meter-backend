package org.tommi.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount extends AbstractPersistable<Long> {

    // @NotEmpty(message = "Käyttäjänimi pitää olla")
    // @Size(min = 3, message = "Käyttäjänimessä pitää olla vähintään 3 merkkiä")
    // @Size(max = 40, message = "Käyttäjänimessä saa olla enintään 40")
    private String name;

    private String password;

    private boolean admin;

    @OneToMany(mappedBy="trainer")
    private List<Cycle> cycles;

    // @Min(value = 12, message = "Ei alle 12-vuotiaita")
    // @Max(value = 100, message = "Ei yli 100-vuotiaita")
    private int age;

    // @Min(value = 40)
    // @Max(value = 150)
    private double weigth;

    // @Min(value = 140)
    // @Max(value = 220)
    private double heigth;

    // @Min(value = 20)
    // @Max(value = 300)
    private double bestSquat;

    // @Min(value = 20)
    // @Max(value = 200)
    private double bestBenchPress;

    // @Min(value = 20)
    // @Max(value = 200)
    private double bestBarbellRow;

    // @Min(value = 20)
    // @Max(value = 150)
    private double bestOverheadPress;

    // @Min(value = 20)
    // @Max(value = 300)
    private double bestDeadlift;
}
