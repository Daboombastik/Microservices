package fr.app.customer.service;

import fr.app.customer.entity.Customer;
import fr.app.customer.entity.CustomerDTO;
import fr.app.notification.entity.NotificationDTO;
import fr.app.verification.entity.VerificationHistoryDTO;
import org.springframework.stereotype.Service;
import fr.app.customer.repository.CustomerRepository;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(CustomerRepository repository, RestTemplate restTemplate) {

    public void registerCustomer(CustomerDTO request) {
        var customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        this.repository.saveAndFlush(customer);
        //-------------------
        VerificationHistoryDTO response = this.restTemplate.getForObject(
                "http://verification/api/v1/verification/{customerId}",
//                "http://localhost:8082/api/v1/verification/{customerId}",
                VerificationHistoryDTO.class,
                customer.getId());
        assert response != null;
        if (response.isFraudster()) {
            throw new IllegalArgumentException("Attention: fraud detected");
        }
        //-------------------
        this.restTemplate.postForObject(
                "http://notification/api/v1/notification",
                new NotificationDTO(customer.getId(),customer.getEmail(),String.format("Hi %s!", customer.getFirstName())),
                NotificationDTO.class);
    }

    private void checkForFraud(CustomerDTO request) {}
}
