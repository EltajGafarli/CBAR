package org.example.cbarcurrencyproject.repository;

import org.example.cbarcurrencyproject.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>, JpaSpecificationExecutor<Currency> {

    Optional<Currency> findByCode(String code);

    List<Currency> findAllByOrderByValueAscCodeAsc();

    @Query(value = "select currency.code from Currency currency ")
    List<String> findAllCodes();


}
