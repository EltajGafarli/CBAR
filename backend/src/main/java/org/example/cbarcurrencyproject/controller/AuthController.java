package org.example.cbarcurrencyproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cbarcurrencyproject.dto.JwtResponseDto;
import org.example.cbarcurrencyproject.dto.LoginRequest;
import org.example.cbarcurrencyproject.dto.PasswordResetDto;
import org.example.cbarcurrencyproject.dto.RegisterRequest;
import org.example.cbarcurrencyproject.service.AuthService;
import org.example.cbarcurrencyproject.service.PasswordResetTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final PasswordResetTokenService passwordResetTokenService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        log.info(request.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        authService.register(request)
                );
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("LoginRequest: {}", loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authentication(loginRequest));
    }


    @PostMapping(path = "/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        return ResponseEntity
                .ok(
                        passwordResetTokenService.resetPassword(
                                passwordResetDto.getResetToken(),
                                passwordResetDto.getNewPassword()
                        )
                );
    }

    @GetMapping(path = "/reset_request")
    public ResponseEntity<String> resetRequest(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        passwordResetTokenService.generateResetToken(userDetails)
                );
    }

}