package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount extends AbstractPersistable<Long> {

    @OneToMany(mappedBy="trainer")
    private ArrayList<Cycle> cycles;

    @NotEmpty(message = "Käyttäjänimi pitää olla")
    @Size(min = 3, message = "Käyttäjänimessä pitää olla vähintään 3 merkkiä")
    @Size(max = 40, message = "Käyttäjänimessä saa olla enintään 40")
    private String name;

    private boolean admin;

    @Size(min = 12, message = "Ei alle 12-vuotiaita")
    @Size(max = 100, message = "Ei yli 100-vuotiaita")
    private int age;

    @Size(min = 40)
    @Size(max = 150)
    private double weigth;

    @Size(min = 140)
    @Size(max = 220)
    private double heigth;

    @Size(min = 20)
    @Size(max = 300)
    private double bestSquat;

    @Size(min = 20)
    @Size(max = 200)
    private double bestBenchPress;

    @Size(min = 20)
    @Size(max = 200)
    private double bestBarbellRow;

    @Size(min = 20)
    @Size(max = 150)
    private double bestOverheadPress;

    @Size(min = 20)
    @Size(max = 300)
    private double bestDeadlift;
}
