package com.vacation.platform.stayfinder;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest(classes = StayFinderApplication.class)
@AutoConfigureMockMvc
public class TermsUserAgreementTest {

    @Test
    public void testMethod2() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        System.out.println(now.format(formatter));
    }


    @Test
    public void testMethod() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        System.out.println(now.format(formatter));
    }

}
