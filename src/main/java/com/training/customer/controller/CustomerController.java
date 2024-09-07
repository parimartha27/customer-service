package com.training.customer.controller;

import com.training.customer.dto.ApiResponse;
import com.training.customer.dto.CreateCustomerRequest;
import com.training.customer.dto.UpdateCustomerRequest;
import com.training.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllCustomer(){
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> addCustomer(@RequestBody CreateCustomerRequest request){
        return new ResponseEntity<>(customerService.createCustomerData(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerRequest request){
        return new ResponseEntity<>(customerService.updateCustomerData(id, request), HttpStatus.OK);

    }

}
