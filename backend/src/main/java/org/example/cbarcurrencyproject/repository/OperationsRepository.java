package org.example.cbarcurrencyproject.repository;

import org.example.cbarcurrencyproject.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsRepository extends JpaRepository<Operations, Long> {
}
