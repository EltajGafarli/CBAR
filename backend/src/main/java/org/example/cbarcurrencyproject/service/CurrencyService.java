package org.example.cbarcurrencyproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cbarcurrencyproject.client.CBARApiClient;
import org.example.cbarcurrencyproject.dto.ConvertedCurrencyDto;
import org.example.cbarcurrencyproject.dto.CurrencyDto;
import org.example.cbarcurrencyproject.dto.CurrencyRequest;
import org.example.cbarcurrencyproject.entity.Currency;
import org.example.cbarcurrencyproject.exception.NotFoundException;
import org.example.cbarcurrencyproject.filter.CurrencySpecification;
import org.example.cbarcurrencyproject.repository.CurrencyRepository;
import org.example.cbarcurrencyproject.xml.ValCurs;
import org.example.cbarcurrencyproject.xml.ValType;
import org.example.cbarcurrencyproject.xml.Valute;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CBARApiClient cbarApiClient;


    @Transactional
    public void fetchAndSaveCurrencies() throws JAXBException {
        String xmlData = getCBAR();

        JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ValCurs valCurs = (ValCurs) unmarshaller.unmarshal(new StringReader(xmlData));

        List<ValType> valTypes = valCurs.getValTypes();
        for (ValType valType : valTypes) {
            for (Valute valute : valType.getValutes()) {

                Optional<Currency> currencyByCode = currencyRepository.findByCode(valute.getCode());

                Currency currency;

                if (currencyByCode.isPresent()) {
                    currency = currencyByCode.get();
                } else {
                    currency = new Currency();
                    currency.setCode(valute.getCode());
                }
                currency.setName(valute.getName());
                currency.setNominal(Integer.parseInt(valute.getNominal().replaceAll("[^\\d]", "")));
                currency.setValue(Double.parseDouble(valute.getValue()));
                currencyRepository.save(currency);


                log.info("{}", currency);

            }
        }
    }


    public List<CurrencyDto> getCurrencies() {
        return currencyRepository.findAllByOrderByValueAscCodeAsc().stream()
                .map(this::currencyToDto)
                .toList();
    }


    public ConvertedCurrencyDto convertCurrency(CurrencyRequest currencyRequest) {
        double actualAmount = Double.parseDouble(currencyRequest.getActualAmount());
        String sourceCode = currencyRequest.getSourceCode();
        String targetCode = currencyRequest.getTargetCode();

        Currency sourceCurrency = currencyRepository.findByCode(sourceCode).orElseThrow(
                () -> new NotFoundException("currency Not found with this code: " + sourceCode)
        );

        Currency targetCurrency = currencyRepository.findByCode(targetCode)
                .orElseThrow(() -> new NotFoundException("currency Not found with this code: " + targetCode));

        return ConvertedCurrencyDto
                .builder()
                .amount(
                        this.calculateCurrency(
                                actualAmount, sourceCurrency.getNominal(), targetCurrency.getNominal(), sourceCurrency.getValue(), targetCurrency.getValue()
                        )
                )
                .code(targetCode)
                .build();

    }

    public List<CurrencyDto> findAllByNameOrCode(String input) {
        Specification<Currency> specification = Specification.where(
                CurrencySpecification.currencySpecificationCode(input)
        ).or(
                CurrencySpecification.currencySpecificationName(input)
        );

        return currencyRepository.findAll(specification)
                .stream()
                .map(this::currencyToDto)
                .toList();
    }


    public List<String> findAllCodes() {
        return this.currencyRepository.findAllCodes();
    }


    private double calculateCurrency(double actualAmount, int sourceNominal, int targetNominal, double sourceValue, double targetValue) {
        double currencyValue = (actualAmount * (sourceValue) / (double) sourceNominal) * ((double) targetNominal / targetValue);
        String currencyValueString = String.format("%.4f", currencyValue);
        return Double.parseDouble(currencyValueString);
    }


    private CurrencyDto currencyToDto(Currency currency) {
        return CurrencyDto
                .builder()
                .name(currency.getName())
                .code(currency.getCode())
                .value(currency.getValue())
                .nominal(currency.getNominal())
                .build();
    }

    private String getCBAR() {
        return cbarApiClient.getCBAR();
    }
}
