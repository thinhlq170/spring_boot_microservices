package com.thinhlq.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        final Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if the email valid
        // todo: check if the email not taken
        customerRepository.saveAndFlush(customer);
        // Check if the customer is a fraud
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );
        final ResponseEntity<FraudCheckResponse> response = fraudClient.isFraudster(customer.getId());
        final FraudCheckResponse fraudCheckResponse = response.getBody();

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: send notification
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}
