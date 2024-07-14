package org.example.cbarcurrencyproject.scheduler;


import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {
    private final CurrencyService currencyService;

    //    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "*/2 * * * * ?")
    @Scheduled(fixedRate = 86400000)
    public void saveCurrencies() throws JAXBException {
        this.currencyService.fetchAndSaveCurrencies();
    }
}
