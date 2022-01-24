package fr.app.notification.entity;

public record NotificationDTO(
        Long toCustomerId,
        String toCustomerName,
        String message
) {}