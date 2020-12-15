package org.tommi.back.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.tommi.back.repositories.UserAccountRepository;

import static org.junit.Assert.*;

@DataJpaTest
//@RunWith(SpringRunner.class)
public class UserAccountRepositoryTest {

    /*
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserAccountRepository userAccountRepository;


     */
    @Test
    public void testTest() {
        assertEquals(1, 1);
    }

}