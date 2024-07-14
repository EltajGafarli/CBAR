package org.example.cbarcurrencyproject.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.cbarcurrencyproject.entity.User;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static org.example.cbarcurrencyproject.util.JavaMailSenderConstants.*;


@Service
@RequiredArgsConstructor
public class MailSenderService {
    private final JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendVerificationCodeToAdmin(User admin, String verificationCode) {
        String replaceContent = ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername()) + " " + verificationCode;
        sendEmail(admin.getEmail(), replaceContent, verificationCode);
    }

    @SneakyThrows
    public void sendVerificationCodeToUser(User user, String verificationCode) {
        String replacedContent = USER_REGISTRATION_CONTENT.replace(USER_KEY, user.getUsername()) + " " + verificationCode;
        sendEmail(user.getEmail(), replacedContent, verificationCode);
    }


    public void sendPasswordResetTokenToAdmin(User admin, String resetToken) {
        String replacedContent = ADMIN_PASSWORD_RESET_TOKEN.replace(ADMIN_KEY, admin.getUsername());
        sendEmail(admin.getEmail(), replacedContent, resetToken);
    }


    public void sendPasswordResetTokenToUser(User user, String resetToken) {
        String replacedContent = USER_PASSWORD_RESET_TOKEN.replace(USER_KEY, user.getUsername());
        sendEmail(user.getEmail(), replacedContent, resetToken);
    }


    public void sendToUser(User user, String adminUsername) {
        String replacedContent = SEND_TO_USER_CONTENT.replace(USER_KEY, user.getUsername());
        replacedContent = replacedContent.replace(ADMIN_KEY, adminUsername);
        sendEmail(user.getEmail(), replacedContent, SEND_TO_USER_SUBJECT);
    }

    public String adminToUserMessage(User user, String adminUsername) {
        String replacedContent = SEND_TO_USER_CONTENT.replace(USER_KEY, user.getUsername());
        replacedContent = replacedContent.replace(ADMIN_KEY, adminUsername);
        return replacedContent;
    }


    public void sendToAdmin(User admin) {
        String replacedContent = ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername());
        sendEmail(admin.getEmail(), replacedContent, SEND_TO_ADMIN_SUBJECT);
    }

    public String systemToAdminMessage(User admin) {
        return ADMIN_REGISTRATION_CONTENT.replace(ADMIN_KEY, admin.getUsername());
    }

    public void sendEmail(String toAddress, String content, String subject) {
        MimeMessage message = message();
        mimeMessageHelper(message, toAddress, content, subject);
        javaMailSender.send(message);
    }


    private MimeMessage message() {
        return javaMailSender.createMimeMessage();
    }

    @SneakyThrows
    private void mimeMessageHelper(MimeMessage message, String toAddress, String content, String subject) {
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(SENDER_ADDRESS, SENDER_NAME);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
    }
}