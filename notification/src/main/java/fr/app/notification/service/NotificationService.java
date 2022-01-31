package fr.app.notification.service;

import fr.app.notification.entity.Notification;
import fr.app.notification.entity.NotificationDTO;
import fr.app.notification.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record NotificationService(NotificationRepository notificationRepository) {

    @RabbitListener(queues = "${rabbitmq.queues.notification}", errorHandler = "validationErrorHandler")
    public void consume(NotificationDTO request) {
        send(request);
    }

    public void send(NotificationDTO request) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(request.toCustomerId())
                        .toCustomerEmail(request.toCustomerName())
                        .sender("Admin")
                        .message(request.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
