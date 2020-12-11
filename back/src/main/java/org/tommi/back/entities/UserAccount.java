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

    @OneToMany(mappedBy="owner")
    private List<Cycle> cycles;

    private double bestSquat;

    private double bestBenchPress;

    private double bestBarbellRow;

    private double bestOverheadPress;

    private double bestDeadlift;

    public UserAccount(String username,
                       String password,
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

        this.bestBarbellRow = bestBarbellRow;
        this.bestBenchPress = bestBenchPress;
        this.bestDeadlift = bestDeadlift;
        this.bestOverheadPress = bestOverheadPress;
        this.bestSquat = bestSquat;
    }
}
