package org.example.cbarcurrencyproject.client;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cbarcurrencyproject.entity.Operations;
import org.example.cbarcurrencyproject.repository.OperationsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class CBARApiClient {
    private final RestTemplate restTemplate;
    private final OperationsRepository operationsRepository;

    @Value(value = "${cbar.url}")
    private String apiUrl;

    @Transactional
    public String getCBAR() {
        String sourceUrl = apiUrl + getCurrentDate() + ".xml";
        Operations operations = Operations
                .builder()
                .operationDate(LocalDateTime.now())
                .build();

        operationsRepository.save(operations);
        return restTemplate.getForEntity(sourceUrl, String.class).getBody();
    }


    private String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(formatter);
    }
}
