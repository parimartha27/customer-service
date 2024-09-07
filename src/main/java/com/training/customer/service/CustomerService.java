package com.training.customer.service;

import com.training.customer.dto.ApiResponse;
import com.training.customer.dto.CreateCustomerRequest;
import com.training.customer.dto.CustomerResponse;
import com.training.customer.dto.UpdateCustomerRequest;
import com.training.customer.entity.CustomerEntity;
import com.training.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ApiResponse getAllCustomer(){
        List<CustomerEntity> customers = customerRepository.findAll();
        List<CustomerResponse> listCustomer = new ArrayList<>();

        customers.forEach(customer -> {
           CustomerResponse response = mappingCustomerData(customer);
           listCustomer.add(response);
        });

        return mappingResponse("C-200", "Success get all customers data",listCustomer);
    }

    public ApiResponse getCustomerById(Long id){

        CustomerEntity customer = findCustomerById(id);
        CustomerResponse response = mappingCustomerData(customer);

        return mappingResponse("C-200", "Success find customer data", response);
    }

    public ApiResponse createCustomerData(CreateCustomerRequest request){

        // TODO: add validation

        CustomerEntity customer = CustomerEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();

        saveCustomerData(customer);

        return mappingResponse("C-201", "Success create customer data", null);
    }

    public ApiResponse deleteCustomer(Long id){
        CustomerEntity customer = findCustomerById(id);
        deleteCustomerData(customer);

        return mappingResponse("C-200", "Success delete customer data", null);
    }

    public ApiResponse updateCustomerData(Long id, UpdateCustomerRequest request){

        //TODO: Add validation for request parameters

        CustomerEntity customer = findCustomerById(id);

        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());

        saveCustomerData(customer);

        return mappingResponse("C-200", "Success update customer data", null);
    }

    private CustomerEntity findCustomerById(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    private CustomerResponse mappingCustomerData(CustomerEntity customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }

    private void deleteCustomerData(CustomerEntity customer) {
        customerRepository.delete(customer);
    }

    private ApiResponse mappingResponse(String errorCode, String errorMessage, Object data){
        return ApiResponse.builder()
                .errorSchema(ApiResponse.ErrorSchema.builder()
                        .errorCode(errorCode)
                        .errorMessage(errorMessage)
                        .build())
                .outputSchema(data)
                .build();
    }

    public void saveCustomerData(CustomerEntity customer){
        customerRepository.save(customer);
    }
}
