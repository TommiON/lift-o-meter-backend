package org.tommi.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;

import java.util.ArrayList;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void addUser(ArrayList<Cycle> cycles,
                        String name,
                        boolean admin,
                        int age,
                        double weight,
                        double height,
                        double bestSquat,
                        double bestBenchPress,
                        double bestBarbellRow,
                        double bestOverheadPress,
                        double bestDeadlift) throws Exception {
        UserAccount user = new UserAccount(cycles, name, admin, age, weight, height, bestSquat, bestBenchPress, bestBarbellRow, bestOverheadPress, bestDeadlift);
        userAccountRepository.save(user);
    }

    public String getUsers() throws Exception {
        ArrayList<UserAccount> users = new ArrayList<>(userAccountRepository.findAll());
        String usersAsString = "";
        for (UserAccount user: users) {
            usersAsString = usersAsString + user.getId() + ": " + user.toString() + System.lineSeparator();
        }
        return usersAsString;
    }
}
