package fr.app.customer.service;

import fr.app.customer.entity.Customer;
import fr.app.customer.entity.CustomerDTO;
import org.springframework.stereotype.Service;
import fr.app.customer.repository.CustomerRepository;

@Service
public record CustomerService(CustomerRepository repository) {

    public void registerCustomer(CustomerDTO request) {
        var customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        this.repository.save(customer);
    }
}
