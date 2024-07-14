package org.example.cbarcurrencyproject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class CbarCurrencyProjectApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CbarCurrencyProjectApplication.class, args);
    }

}
