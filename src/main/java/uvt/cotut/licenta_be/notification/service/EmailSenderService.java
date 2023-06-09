package uvt.cotut.licenta_be.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.model.Order;
import uvt.cotut.licenta_be.model.OrderAmount;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendConfirmationEmail(String toEmail, Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Confirmation for your order #" + order.getId());
        StringBuilder text = new StringBuilder("This is a confirmation email for your order #" + order.getId() + "\n\nOrder contains:\n");
        for (OrderAmount orderAmount : order.getOrderAmountList()) {
            text.append(orderAmount.getAmount()).append(" ").append(orderAmount.getProduct().getName()).append("\n");
        }
        text.append("\nYou will be notified with the progress of your order,\nHave a good day!");
        message.setText(text.toString());

        mailSender.send(message);
    }
}
