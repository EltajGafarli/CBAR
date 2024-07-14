package org.example.cbarcurrencyproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationDto {
    private String email;
    private String verificationCode;
}