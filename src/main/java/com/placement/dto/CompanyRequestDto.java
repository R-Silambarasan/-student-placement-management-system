package com.placement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
public class CompanyRequestDto {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "Company email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Industry is required")
    private String industry;

    @NotBlank(message = "Location is required")
    private String location;

    private String website;
}