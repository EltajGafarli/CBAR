package org.example.cbarcurrencyproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cbarcurrencyproject.dto.JwtResponseDto;
import org.example.cbarcurrencyproject.dto.LoginRequest;
import org.example.cbarcurrencyproject.dto.RegisterRequest;
import org.example.cbarcurrencyproject.entity.Role;
import org.example.cbarcurrencyproject.entity.User;
import org.example.cbarcurrencyproject.entity.enums.RoleEnum;
import org.example.cbarcurrencyproject.exception.AlreadyExistException;
import org.example.cbarcurrencyproject.exception.NotFoundException;
import org.example.cbarcurrencyproject.exception.UserNotVerifiedException;
import org.example.cbarcurrencyproject.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final JwtService jwtService;




    public String register(RegisterRequest request) {

        userRepository.findUserByEmailOrNameOfUser(request.getEmail(), request.getUserName())
                .ifPresent(
                        data -> {
                            throw new AlreadyExistException("User already exist!");
                        }
                );

        User user = getUser(request);
        user.addRole(
                Role
                        .builder()
                        .role(RoleEnum.USER)
                        .build()
        );


        userRepository.save(user);
        verificationService.verificationCodeSending(user);
        return "Register";
    }


    public JwtResponseDto authentication(LoginRequest loginRequest) {

        userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String email = loginRequest.getEmail();
        User user = getUserByEmail(email);
        if (user.isEnabled()) {
            String jwt = jwtService.generateToken(user);
            log.info("JWT generated: {}", jwt);
            return getToken(jwt);
        }

        throw new UserNotVerifiedException("User is not verified. Please verify your account.");
    }


    private User getUser(RegisterRequest request) {
        return User.builder()
                .nameOfUser(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }



    private JwtResponseDto getToken(String jwt) {
        return JwtResponseDto.builder()
                .accessToken(jwt)
                .build();
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with this email."));
    }

}