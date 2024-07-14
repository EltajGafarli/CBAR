package org.example.cbarcurrencyproject.repository;

import org.example.cbarcurrencyproject.entity.Role;
import org.example.cbarcurrencyproject.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRole(RoleEnum role);
}