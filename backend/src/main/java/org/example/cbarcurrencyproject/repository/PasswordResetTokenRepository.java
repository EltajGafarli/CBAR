package org.example.cbarcurrencyproject.repository;

import org.example.cbarcurrencyproject.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findPasswordResetTokenByResetPasswordToken(String resetPasswordToken);
}