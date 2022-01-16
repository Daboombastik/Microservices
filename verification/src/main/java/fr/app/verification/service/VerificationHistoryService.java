package fr.app.verification.service;

import fr.app.verification.entity.VerificationHistory;
import fr.app.verification.repository.VerificationHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record VerificationHistoryService(VerificationHistoryRepository repository) {

    public boolean isFraudulentCustomer(Long customerID) {
        repository.save(VerificationHistory.builder()
                .customerId(customerID)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }
}
