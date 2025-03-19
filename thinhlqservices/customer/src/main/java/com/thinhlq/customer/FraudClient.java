package com.thinhlq.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "fraud-service", url = "http://localhost:8081")
public interface FraudClient {

    @GetMapping("/api/v1/fraud-check/{customerId}")
    public ResponseEntity<FraudCheckResponse> isFraudster(@PathVariable("customerId") int customerId);
}
