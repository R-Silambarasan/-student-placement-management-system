
package com.placement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.placement.enums.JobDriveStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDriveResponseDto {

    private Long id;
    private Long companyId;
    private String jobTitle;
    private String description;
    private Double minimumCgpa;
    private Integer maximumBacklogs;
    private String eligibleDepartment;
    private LocalDate driveDate;
    private String location;
    private JobDriveStatus status;
    private LocalDateTime applicationDeadline;
}

