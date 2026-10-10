
package com.placement.dto;

import java.time.LocalDate;

import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;

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
public class InterviewResponseDto {

    private Long id;

    private Long applicationId;

    private InterviewRound round;

    private LocalDate interviewDate;

    private String interviewer;

    private InterviewResult result;
}

