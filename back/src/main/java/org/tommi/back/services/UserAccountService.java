package org.tommi.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tommi.back.entities.Cycle;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> getUsers() throws Exception {
        return userAccountRepository.findAll();
    }

    public UserAccount createUser(UserAccount userAccount) throws Exception {
        return userAccountRepository.save(userAccount);
    }

    public void deleteUser(Long id) throws Exception {
        userAccountRepository.deleteById(id);
    }
}
