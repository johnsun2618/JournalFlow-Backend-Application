package com.example.JournalApp.Repository;

import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    @Disabled("tested")
    void testSaveNewUser(){
        Assertions.assertNotNull(userRepository.getUserForSA());
    }

}
