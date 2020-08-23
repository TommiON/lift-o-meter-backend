package org.tommi.back.repositories;

import org.tommi.back.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tommi.back.entities.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
