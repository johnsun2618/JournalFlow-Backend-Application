package com.example.JournalApp.Service;

import com.example.JournalApp.Entity.User;
import com.example.JournalApp.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static  org.mockito.Mockito.*;

@ActiveProfiles("dev")
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Disabled
    void loadUserByUserNameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
                .thenReturn(User.builder()
                        .userName("anmol")
                        .password("anmol2618")
                        .roles(new ArrayList<>())
                        .build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }

}
