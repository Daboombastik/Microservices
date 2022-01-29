package fr.app.customer.controller;

import fr.app.customer.entity.CustomerDTO;
import fr.app.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public record CustomerController(CustomerService service) {

    @PostMapping("api/v1/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer) {
        log.info("Customer was saved: {}", customer);
        service.registerCustomer(customer);
        return ResponseEntity.ok(customer);
    }
}

//      {
//        "firstName":"Clara",
//        "lastName":"Maria",
//        "email":"clara_maria@email.com"
//       }
