
package com.placement.dto;

import java.time.LocalDate;

import com.placement.enums.OfferStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferRequestDto {

    private Long id;

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Offer date is required")
    @FutureOrPresent(message = "Offer date cannot be in the past")
    private LocalDate offerDate;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    private Double salary;

    @NotNull(message = "Joining date is required")
    @FutureOrPresent(message = "Joining date cannot be in the past")
    private LocalDate joiningDate;

    @NotNull(message = "Offer status is required")
    private OfferStatus status;
    
   
    @NotNull(message = "Response deadline is required")
    @FutureOrPresent(message = "Response deadline cannot be in the past")
    private LocalDate responseDeadline;
    
}

