package fr.app.notification.controller;

import fr.app.notification.entity.NotificationDTO;
import fr.app.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/notification")
@Slf4j
public record NotificationController(NotificationService notificationService) {

    @PostMapping
    public void sendNotification(@RequestBody NotificationDTO request) {
        log.info("New notification... {}", request);
        notificationService.send(request);
    }
}
