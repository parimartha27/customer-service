package com.training.customer.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "CUSTOMER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can't be null, empty or blank")
    private String name;

    @Email(message = "Email not valid")
    @NotBlank(message = "Email can't be null, empty or blank")
    private String email;

    @NotBlank(message = "Phone Number can't be null, empty or blank")
    private String phoneNumber;

    private String address;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @CreationTimestamp
    private LocalDateTime updatedDate;
}
