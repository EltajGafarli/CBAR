package org.example.cbarcurrencyproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}