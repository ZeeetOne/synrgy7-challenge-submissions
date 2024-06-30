package com.binarfud.notification_service.consumer;

import com.binarfud.notification_service.dto.OrderCompletedEventDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @KafkaListener(topics = "order-completed", groupId = "group-id")
    public void listenOrderCompleted(String message) {
        System.out.println("Message received: " + message);
        try {
            OrderCompletedEventDto eventDto = objectMapper.readValue(message, OrderCompletedEventDto.class);

            // Build the email message
            StringBuilder orderDetailMessage = new StringBuilder();
            orderDetailMessage.append("======================================\n")
                    .append("BinarFud\n")
                    .append("======================================\n")
                    .append("Terima kasih sudah memesan di BinarFud\n")
                    .append("======================================\n")
                    .append("Order ID: ").append(eventDto.getId()).append("\n")
                    .append("Order Time: ").append(eventDto.getOrderTime()).append("\n")
                    .append("======================================\n")
                    .append("Order Details:\n");

            int totalQuantity = 0;
            double totalPrice = 0;

            for (OrderCompletedEventDto.OrderCompletedDetailDto detail : eventDto.getOrderDetails()) {
                orderDetailMessage.append(detail.getProductName()).append("\t")
                        .append(detail.getQuantity()).append("\t")
                        .append(detail.getPrice()).append("\n");

                totalQuantity += detail.getQuantity();
                totalPrice += detail.getPrice();
            }

            orderDetailMessage.append("--------------------------------------\n")
                    .append("Total\t").append(totalQuantity).append("\t").append(totalPrice).append("\n")
                    .append("--------------------------------------\n")
                    .append("Pembayaran : BinarCash\n")
                    .append("======================================\n")
                    .append("Simpan struk ini sebagai bukti pembayaran\n")
                    .append("======================================\n");

            // Send email
            sendMail(eventDto.getUserEmail(), "Your Order is Completed", orderDetailMessage.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMail(String mailAddress, String title, String mailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username"));
        message.setTo(mailAddress);
        message.setSubject(title);
        message.setText(mailMessage);
        javaMailSender.send(message);
    }
}
