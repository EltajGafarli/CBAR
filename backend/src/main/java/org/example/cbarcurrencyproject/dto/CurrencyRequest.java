package org.example.cbarcurrencyproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrencyRequest {

    @NotNull(message = "Actual amount cannot be null")
    @Pattern(regexp = "^[+]?\\d*(\\.\\d+)?$", message = "Value must be a valid double number or integer")
    private String actualAmount;

    @NotBlank(message = "Source code cannot be blank")
    @NotNull(message = "Source code cannot be null")
    @NotEmpty(message = "Source code cannot be empty")
    private String sourceCode;

    @NotBlank(message = "Target code cannot be blank")
    @NotNull(message = "Target code cannot be null")
    @NotEmpty(message = "Target code cannot be empty")
    private String targetCode;
}
