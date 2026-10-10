
package com.placement.dto;

import com.placement.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;

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
public class ApplicationRequestDto {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Job drive ID is required")
    private Long jobDriveId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private Boolean shortlisted;
}