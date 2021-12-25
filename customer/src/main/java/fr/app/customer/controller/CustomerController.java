package fr.app.customer.controller;

import fr.app.customer.entity.CustomerDTO;
import fr.app.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService service) {

    @PostMapping
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer) {
        log.info("Customer was saved: {}", customer);
        service.registerCustomer(customer);
        return ResponseEntity.<CustomerDTO>ok(customer);
    }
}
