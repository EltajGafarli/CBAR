package org.example.cbarcurrencyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrencyDto {
    private String code;
    private String name;
    private int nominal;
    private double value;
}
