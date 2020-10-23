package org.tommi.back.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserAccount extends AbstractPersistable<Long> {

    @NotBlank
    // @Size(min = 3, message = "Käyttäjänimessä pitää olla vähintään 3 merkkiä")
    // @Size(max = 40, message = "Käyttäjänimessä saa olla enintään 40")
    private String username;

    @NotBlank
    private String password;

    // private boolean admin;

    @ManyToMany(cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<UserRole> roles = new HashSet<>();

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

    public UserAccount(String username, String password) {
        this.username = username;
        this.password = password;

        UserRole role = new UserRole(RoleEnum.ROLE_ADMIN);
        this.roles = new HashSet<>();
        this.roles.add(role);

        this.age = 0;
        this.bestBarbellRow = 0;
        this.bestBenchPress = 0;
        this.bestDeadlift = 0;
        this.bestOverheadPress = 0;
        this.bestSquat = 0;
        this.heigth = 0;
        this.weigth = 0;
    }
}
