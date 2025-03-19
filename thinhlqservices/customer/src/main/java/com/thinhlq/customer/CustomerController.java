package com.thinhlq.customer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRequest) {
        log.info("new customer registration {}", customerRequest);
        customerService.registerCustomer(customerRequest);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers() {
        // TODO: consider to handle exception, API failing with corresponding http status code
        final List<Customer> customers = customerService.findAllCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }
}
