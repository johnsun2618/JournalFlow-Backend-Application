package com.example.JournalApp.Service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    @Disabled
    void testSendMail(){
        emailService.sendEmail("johnsun2618@gmail.com",
                "Testing Java Mail Sender",
                "Hi, All Good??");
    }

}
