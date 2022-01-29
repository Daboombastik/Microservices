package fr.app.customer.service;

import fr.app.customer.entity.Customer;
import fr.app.customer.entity.CustomerDTO;
import fr.app.notification.entity.NotificationDTO;
import fr.app.verification.entity.VerificationHistoryDTO;
import fr.app.amqp.producer.RabbitMQProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import fr.app.customer.repository.CustomerRepository;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    CustomerRepository repository;
    RestTemplate restTemplate;
    RabbitMQProducer producer;

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
        NotificationDTO notification = new NotificationDTO(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s!", customer.getFirstName())
        );
        /*instead of direct call to notification use rabbitMQ
                this.restTemplate.postForObject(
                "http://notification/api/v1/notification",
                notification,
                NotificationDTO.class);
         */
        producer.publish(
                "internal.exchange",
                "internal.notification.routing-key",
                notification
        );
    }
}
