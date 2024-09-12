package com.training.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.customer.constant.Constant;
import com.training.customer.dto.*;
import com.training.customer.entity.CustomerEntity;
import com.training.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ResponseEntity<Object> getAllCustomer(){
        List<CustomerEntity> customers = customerRepository.findAll();
        List<CustomerResponse> listCustomer = new ArrayList<>();

        customers.forEach(customer -> {
           CustomerResponse response = mappingCustomerData(customer);
           listCustomer.add(response);
        });

        return createResponse(Constant.SUCCESS, "Success get all customers data",listCustomer, HttpStatus.OK);
    }

    public ResponseEntity<Object> getCustomerById(Long id){

        CustomerEntity customer = findCustomerById(id);

        if(customer == null){
            return createResponse(Constant.NOT_FOUND, "Customer not found", null, HttpStatus.NOT_FOUND);
        }

        CustomerResponse response = mappingCustomerData(customer);
        return createResponse(Constant.SUCCESS, "Success find customer data", response, HttpStatus.OK);
    }

    public ResponseEntity<Object> createCustomerData(CreateCustomerRequest request){

        CustomerEntity customer = CustomerEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .accountNumber(request.getAccountNumber())
                .balance(request.getBalance())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        saveCustomerData(customer);

        return createResponse(Constant.CREATED, "Success create customer data", null, HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteCustomer(Long id){
        CustomerEntity customer = findCustomerById(id);

        if(customer == null){
            return createResponse(Constant.NOT_FOUND, "Data not found", null, HttpStatus.NOT_FOUND);
        }

        deleteCustomerData(customer);

        return createResponse(Constant.SUCCESS, "Success delete customer data", null, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateCustomerData(Long id, UpdateCustomerRequest request){

        CustomerEntity customer = findCustomerById(id);

        if(customer == null){
            return createResponse(Constant.NOT_FOUND, "Data not found", null, HttpStatus.NOT_FOUND);
        }

        customer.setName(request.getName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());
        customer.setAccountNumber(request.getAccountNumber());
        customer.setBalance(request.getAmount());
        customer.setUpdatedDate(LocalDateTime.now());

        saveCustomerData(customer);

        return createResponse(Constant.SUCCESS, "Success update customer data", null, HttpStatus.OK);
    }

    public ResponseEntity<Object> updateBalance (UpdateBalanceRequest request){

        Optional<CustomerEntity> customerFromDB = customerRepository.findByEmail(request.getEmail());

        if(customerFromDB.isEmpty()){
            return createResponse(Constant.NOT_FOUND, "Customer is not found", null, HttpStatus.NOT_FOUND);
        }

        CustomerEntity customer = customerFromDB.get();

        if(customer.getBalance() < request.getTotalPrice()){
            return createResponse(Constant.BAD_REQUEST, "Amount is not enough", null, HttpStatus.BAD_REQUEST);
        }

        Double updatedBalance = customer.getBalance() - request.getTotalPrice();

        customer.setBalance(updatedBalance);
        saveCustomerData(customer);

        CustomerResponse response = mappingCustomerData(customer);

        return createResponse(Constant.SUCCESS, "Success update balance", response, HttpStatus.OK);
    }

    private CustomerEntity findCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    private CustomerResponse mappingCustomerData(CustomerEntity customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .accountNumber(customer.getAccountNumber())
                .balance(customer.getBalance())
                .build();
    }

    private void deleteCustomerData(CustomerEntity customer) {
        customerRepository.delete(customer);
    }

    private ResponseEntity<Object> createResponse (String errorCode, String errorMessage, Object data, HttpStatus status){
        ApiResponse response = ApiResponse.builder()
                .errorSchema(ApiResponse.ErrorSchema.builder()
                        .errorCode(errorCode)
                        .errorMessage(errorMessage)
                        .build())
                .outputSchema(data)
                .build();

        return new ResponseEntity<>(response, status);
    }

    public void saveCustomerData(CustomerEntity customer){
        customerRepository.save(customer);
    }
}
