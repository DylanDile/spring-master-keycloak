package com.example.springmasterv01.controllers;

import com.example.springmasterv01.core.ApiResponse;
import com.example.springmasterv01.models.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String toString() {
        return "Welcome to DevOps";
    }

    @GetMapping("/customer")
    public ResponseEntity<ApiResponse> getCustomer() {
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .address("123 Main St")
                .city("New York")
                .state("NY")
                .zipCode("12345")
                .email("john@mail.com")
                .build();

        ApiResponse response = ApiResponse.builder()
                .status(true)
                .message("Customer retrieved successfully")
                .data(customer)
                .build();

        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/inputter")
    @PreAuthorize("hasRole('client_inputter')")
    public ResponseEntity<ApiResponse> user() {
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("Inputter")
                .lastName("Doe")
                .phoneNumber("1234567890")
                .address("123 Main St")
                .city("New York")
                .state("NY")
                .zipCode("12345")
                .email("inputter@mail.com")
                .build();

        ApiResponse response = ApiResponse.builder()
                .status(true)
                .message("Customer retrieved successfully")
                .data(customer)
                .build();

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<ApiResponse> admin(){
        ApiResponse response = ApiResponse.builder()
                .status(true)
                .message("Request received successfully")
                .data("Hello Admin")
                .build();

        return ResponseEntity.status(200).body(response);
    }
}
