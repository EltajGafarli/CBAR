package org.example.cbarcurrencyproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cbarcurrencyproject.email.MailSenderService;
import org.example.cbarcurrencyproject.entity.PasswordResetToken;
import org.example.cbarcurrencyproject.entity.User;
import org.example.cbarcurrencyproject.exception.NotFoundException;
import org.example.cbarcurrencyproject.repository.PasswordResetTokenRepository;
import org.example.cbarcurrencyproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;
    private final Random random = new Random();

    public String generateResetToken(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
        String resetToken = generateRandomToken();
        PasswordResetToken passwordResetToken = getPasswordResetToken(user, resetToken);
        sendPasswordToken(user, passwordResetToken.getResetPasswordToken());
        passwordResetTokenRepository.save(passwordResetToken);
        return "Reset token sent successfully";
    }

    public void sendPasswordToken(User user, String passwordToken) {
        mailSenderService.sendPasswordResetTokenToUser(user, passwordToken);
    }

    public User getUserByResetToken(String resetToken) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenByResetToken(resetToken);
        return passwordResetToken.getUser();
    }

    public void deleteResetToken(String resetToken) {
        PasswordResetToken passwordResetToken = getPasswordResetTokenByResetToken(resetToken);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    public String generateRandomToken() {
        int codeLength = 6;
        String characters = "0123456789";
        return IntStream.range(0, codeLength)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    private PasswordResetToken getPasswordResetToken(User user, String resetToken) {
        return PasswordResetToken.builder()
                .user(user)
                .resetPasswordToken(resetToken)
                .build();
    }

    private PasswordResetToken getPasswordResetTokenByResetToken(String resetToken) {
        return passwordResetTokenRepository.findPasswordResetTokenByResetPasswordToken(resetToken)
                .orElseThrow(
                        () -> new NotFoundException("PasswordResetToken not found with resetToken.")
                );
    }


    public String resetPassword(String resetToken, String newPassword) {
        User user = getUserByResetToken(resetToken);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        deleteResetToken(resetToken);
        return "Password reset successful";
    }
}