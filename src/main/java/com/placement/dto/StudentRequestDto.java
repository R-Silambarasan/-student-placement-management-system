package com.placement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "CGPA is required")
    @PositiveOrZero(message = "CGPA cannot be negative")
    private Double cgpa;

    @NotNull(message = "Graduation year is required")
    private Integer graduationYear;

    @NotNull(message = "Backlogs is required")
    @PositiveOrZero(message = "Backlogs cannot be negative")
    private Integer backlogs;

    @NotBlank(message = "Skills are required")
    private String skills;
}