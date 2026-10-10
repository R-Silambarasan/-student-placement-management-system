
package com.placement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.placement.enums.JobDriveStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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
public class JobDriveRequestDto {

    private Long id;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Minimum CGPA is required")
    @PositiveOrZero(message = "CGPA cannot be negative")
    private Double minimumCgpa;

    @NotNull(message = "Maximum backlogs is required")
    @PositiveOrZero(message = "Maximum backlogs cannot be negative")
    private Integer maximumBacklogs;

    @NotBlank(message = "Eligible department is required")
    private String eligibleDepartment;

    @NotNull(message = "Drive date is required")
    @FutureOrPresent(message = "Drive date cannot be in the past")
    private LocalDate driveDate;

    private String location;

    @NotNull(message = "Status is required")
    private JobDriveStatus status;

    @NotNull(message = "Application deadline is required")
    @Future(message = "Application deadline must be in the future")
    private LocalDateTime applicationDeadline;
}

