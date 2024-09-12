package com.training.customer.controller;

import com.training.customer.dto.*;
import com.training.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<Object> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping()
    public ResponseEntity<Object> addCustomer(@RequestBody CreateCustomerRequest request){
        return customerService.createCustomerData(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerRequest request){
        return customerService.updateCustomerData(id, request);

    }

    @PostMapping("/update-balance")
    public ResponseEntity<Object> updateBalance(@RequestBody UpdateBalanceRequest request){
        return customerService.updateBalance(request);
    }

}
