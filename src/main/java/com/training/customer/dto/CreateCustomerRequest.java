package com.training.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
