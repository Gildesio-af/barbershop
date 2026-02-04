package com.barbeshop.api.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendRecoveryEmail(String to, String username, String link) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject("Recuperação de Senha - Barbearia");

            Context context = new Context();
            context.setVariable("title", "Recuperação de Senha");
            context.setVariable("username", username);
            context.setVariable("recoveryLink", link);

            String htmlContent = templateEngine.process("recovery-email", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Recovery email sent to {}", to);
        } catch (Exception e) {
            log.error("Error sending email with template: ", e);
        }
    }
}
