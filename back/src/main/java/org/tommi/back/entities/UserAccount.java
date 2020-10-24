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
    private String username;

    @NotBlank
    private String password;

    @ManyToMany(cascade= CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(mappedBy="trainer")
    private List<Cycle> cycles;

    private int age;

    private double weigth;

    private double heigth;

    private double bestSquat;

    private double bestBenchPress;

    private double bestBarbellRow;

    private double bestOverheadPress;

    private double bestDeadlift;

    public UserAccount(String username,
                       String password,
                       int age,
                       double heigth,
                       double weigth,
                       double bestBarbellRow,
                       double bestBenchPress,
                       double bestDeadlift,
                       double bestOverheadPress,
                       double bestSquat) {
        this.username = username;
        this.password = password;

        UserRole role = new UserRole(RoleEnum.ROLE_USER);
        this.roles = new HashSet<>();
        this.roles.add(role);

        this.age = age;
        this.bestBarbellRow = bestBarbellRow;
        this.bestBenchPress = bestBenchPress;
        this.bestDeadlift = bestDeadlift;
        this.bestOverheadPress = bestOverheadPress;
        this.bestSquat = bestSquat;
        this.heigth = heigth;
        this.weigth = weigth;
    }
}
