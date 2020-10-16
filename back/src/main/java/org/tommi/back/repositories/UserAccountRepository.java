package org.tommi.back.repositories;

import org.tommi.back.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tommi.back.entities.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByName(String name);

}
