package org.example.cbarcurrencyproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;
}