
package com.placement.dto;

import java.time.LocalDate;

import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;

import jakarta.validation.constraints.FutureOrPresent;
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
public class InterviewRequestDto {

    private Long id;

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Interview round is required")
    private InterviewRound round;

    @NotNull(message = "Interview date is required")
    @FutureOrPresent(message = "Interview date cannot be in the past")
    private LocalDate interviewDate;

    private String interviewer;

    @NotNull(message = "Interview result is required")
    private InterviewResult result;
}

