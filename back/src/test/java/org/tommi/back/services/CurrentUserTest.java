package org.tommi.back.services;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.tommi.back.BackApplication;
import org.tommi.back.entities.UserAccount;
import org.tommi.back.repositories.UserAccountRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CurrentUserTest {

    @Autowired
    private CurrentUser currentUser;

    UserAccountRepository repository = Mockito.mock(UserAccountRepository.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    UserAccount testAccount = new UserAccount(
            "Tommi",
            "salis",
            100.0,
            100.0,
            60.0,
            60.0,
            40.0);

    @Test
    public void testtest() {
        // given
        Mockito.when(authentication.getName()).thenReturn("Tommi");
        Mockito.when(repository.findByUsername("Tommi")).thenReturn(java.util.Optional.ofNullable(testAccount));

        // when
        //UserAccount current = currentUser.get();

        // then
        assertEquals(1,1);
    }
}