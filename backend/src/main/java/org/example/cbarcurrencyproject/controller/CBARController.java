package org.example.cbarcurrencyproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cbarcurrencyproject.dto.ConvertedCurrencyDto;
import org.example.cbarcurrencyproject.dto.CurrencyDto;
import org.example.cbarcurrencyproject.dto.CurrencyRequest;
import org.example.cbarcurrencyproject.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cbar")
@RequiredArgsConstructor
@Slf4j
public class CBARController {
    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<List<CurrencyDto>> getCbar() throws JAXBException {
        return ResponseEntity
                .ok(
                        currencyService.getCurrencies()
                );
    }


    @PostMapping(path = "/convert")
    public ResponseEntity<ConvertedCurrencyDto> convertCurrency(@RequestBody @Valid CurrencyRequest currencyRequest) {
        return ResponseEntity
                .ok(
                        this.currencyService.convertCurrency(
                                currencyRequest
                        )
                );
    }

    @GetMapping(path = "/codename")
    public ResponseEntity<List<CurrencyDto>> findAllByCodeOrName(@RequestParam String input) {
        log.info("{}", input);
        return ResponseEntity
                .ok(this.currencyService.findAllByNameOrCode(input));
    }


    @GetMapping(path = "/codes")
    public ResponseEntity<List<String>> findCodes() {
        return ResponseEntity
                .ok(
                        this.currencyService.findAllCodes()
                );
    }

}
