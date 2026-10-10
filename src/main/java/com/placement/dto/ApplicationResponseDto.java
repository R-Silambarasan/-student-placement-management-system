
package com.placement.dto;

import java.time.LocalDateTime;

import com.placement.enums.ApplicationStatus;

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
public class ApplicationResponseDto {

    private Long id;

    private Long studentId;

    private Long jobDriveId;

    private LocalDateTime appliedAt;

    private ApplicationStatus status;

    private Boolean shortlisted;
}