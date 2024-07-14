package org.example.cbarcurrencyproject.controller;


import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.dto.VerificationDto;
import org.example.cbarcurrencyproject.service.VerificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping(path = "/verify")
    public ResponseEntity<String> verify(@RequestBody VerificationDto verification) {
        boolean isVerified = verificationService.verifyUser(
                verification.getEmail(),
                verification.getVerificationCode()
        );

        if (isVerified) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User verified successfully");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Verification failed. Invalid email or verification code.");
    }
}