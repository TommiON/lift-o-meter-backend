package org.tommi.back.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;

@Component
public class UserFactory {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    CycleFactory cycleFactory;

    public void build(
            String username,
            String password,
            double bestBarbellrow,
            double bestBenchpress,
            double bestDeadlift,
            double bestOverheadpress,
            double bestSquat) {
        UserAccount user = new UserAccount(
                username,
                password,
                bestBarbellrow,
                bestBenchpress,
                bestDeadlift,
                bestOverheadpress,
                bestSquat
        );

        Cycle cycle = cycleFactory.build(user, bestSquat, bestDeadlift, bestBenchpress, bestBarbellrow, bestOverheadpress);

        user.addCycle(cycle);

        userAccountRepository.save(user);
    }

    public boolean usernameAlreadyTaken(String username) {
        if(userAccountRepository.existsByUsername(username)) {
            return true;
        }
        return false;
    }
}
