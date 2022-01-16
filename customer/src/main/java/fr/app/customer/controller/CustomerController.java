package fr.app.customer.controller;

import fr.app.customer.entity.CustomerDTO;
import fr.app.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("api/v1")
public record CustomerController(CustomerService service) {

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customer) {
        log.info("Customer was saved: {}", customer);
        service.registerCustomer(customer);
        return ResponseEntity.<CustomerDTO>ok(customer);
    }
}

//      {
//        "firstName":"Clara",
//        "lastName":"Maria",
//        "email":"clara_maria@email.com"
//       }
