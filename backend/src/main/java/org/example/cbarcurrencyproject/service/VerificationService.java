package org.example.cbarcurrencyproject.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.email.MailSenderService;
import org.example.cbarcurrencyproject.entity.User;
import org.example.cbarcurrencyproject.entity.Verification;
import org.example.cbarcurrencyproject.exception.NotFoundException;
import org.example.cbarcurrencyproject.repository.UserRepository;
import org.example.cbarcurrencyproject.repository.VerificationRepository;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class VerificationService {

    private final UserRepository userRepository;
    private final VerificationRepository verificationRepository;
    private final MailSenderService mailSenderService;


    public Boolean verifyUser(String email, String verificationCode) {
        User user = getUserByEmail(email);
        Verification verification = getVerificationByVerificationCode(verificationCode);
        if (user != null && verification != null && !user.isEnabled()
                && user.getId() == verification.getUser().getId()) {
            user.setEnabled(true);
            userRepository.save(user);
            verification.setUser(null);
            verificationRepository.delete(verification);
            return true;
        }
        return false;
    }


    public void verificationCodeSending(User user) {
        String generatedVerificationCode = generateVerificationCode();
        Verification verification = Verification
                .builder()
                .user(user)
                .verificationCode(generatedVerificationCode)
                .build();
        verificationRepository.save(verification);
        mailSenderService.sendVerificationCodeToUser(user, generatedVerificationCode);

    }


    public String generateVerificationCode() {
        int codeLength = 6;
        String characters = "0123456789";
        Random random = new Random();
        return IntStream.range(0, codeLength)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private Verification getVerificationByVerificationCode(String verificationCode) {
        return verificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new NotFoundException("Verification not found with this verification code."));
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
    }
}