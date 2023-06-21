package uvt.cotut.licenta_be.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uvt.cotut.licenta_be.exception.ApplicationBusinessException;
import uvt.cotut.licenta_be.exception.ErrorCode;
import uvt.cotut.licenta_be.model.Order;
import uvt.cotut.licenta_be.model.OrderAmount;
import uvt.cotut.licenta_be.model.ShopInfo;
import uvt.cotut.licenta_be.repository.ShopInfoRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final ShopInfoRepository shopInfoRepository;


    @Value("${spring.mail.username}")
    private String fromEmail;


    public void sendConfirmationEmail(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Confirmation for your order #" + order.getId());
        StringBuilder text = new StringBuilder("This is a confirmation email for your order #" + order.getId() + "\n\nOrder contains:\n");

        float total = 0;
        for (OrderAmount orderAmount : order.getOrderAmountList()) {
            text.append(orderAmount.getAmount()).append(" ").append(orderAmount.getProduct().getName()).append("\n");
            total += orderAmount.getPrice();
        }
        text.append("Total price of the order: ").append(total).append(" lei");

        text.append("\nYou will be notified with the progress of your order,\nHave a good day!\n");
        message.setText(text.toString());

        mailSender.send(message);
    }

    public void sendInDelivery(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Your order #" + order.getId() + " is in delivery");
        StringBuilder text = new StringBuilder("Your order #" + order.getId() + " is in delivery and you will be soon called on your phone number for more information.");
        text.append(" \n\nOrder contains:\n");

        float total = 0;
        for (OrderAmount orderAmount : order.getOrderAmountList()) {
            text.append(orderAmount.getAmount()).append(" ").append(orderAmount.getProduct().getName()).append("\n");
            total += orderAmount.getPrice();
        }
        text.append("Total price of the order you will need to pay: ").append(total).append(" lei\n");

        text.append("\nHave a good day!");
        message.setText(text.toString());

        mailSender.send(message);
    }

    public void sendDelivered(Order order) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Thank you for buying!");
        StringBuilder text = new StringBuilder("Your order #" + order.getId() + " has been delivered\n");

        text.append("We want to thank you for choosing us!");

        text.append("\nHave a good day!");
        message.setText(text.toString());

        mailSender.send(message);
    }

    public void sendCanceled(Order order) {
        ShopInfo shopInfo = shopInfoRepository.findById(1L).orElseThrow(() -> new ApplicationBusinessException(ErrorCode.SHOP_INFO_NOT_FOUND));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(order.getUser().getEmail());
        message.setSubject("Your order #" + order.getId() + " was been canceled");
        StringBuilder text = new StringBuilder("Your order #" + order.getId() + " was been canceled\n");

        text.append("For more information you can contact us on:\n");
        text.append(" -Email: ").append(shopInfo.getContactEmail());
        text.append("\n -Phone number: ").append(shopInfo.getContactPhoneNumber());
        text.append("\n -In our location: ").append(shopInfo.getContactLocation());

        text.append("\n\nHave a good day!");
        message.setText(text.toString());

        mailSender.send(message);
    }
}
