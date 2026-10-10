
package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.InterviewRequestDto;
import com.placement.dto.InterviewResponseDto;
import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;

public interface InterviewService {

    InterviewResponseDto createOrUpdate(
            InterviewRequestDto requestDto);

    InterviewResponseDto getById(Long id);

    List<InterviewResponseDto> getAll();

    void deleteById(Long id);

    List<InterviewResponseDto> getByResult(
            InterviewResult result);

    List<InterviewResponseDto> getByRound(
            InterviewRound round);

    List<InterviewResponseDto> getByApplicationId(
            Long applicationId);

    List<InterviewResponseDto> getByInterviewer(
            String interviewer);

    List<InterviewResponseDto> getByRoundAndResult(
            InterviewRound round,
            InterviewResult result);

    List<InterviewResponseDto> getSelectedInterviews();

    List<InterviewResponseDto> getPendingInterviews();

    Page<InterviewResponseDto> getInterviews(
            int pageNo,
            int pageSize);

    Page<InterviewResponseDto> getInterviewsSorted(
            int pageNo,
            int pageSize,
            String field);

    void updateInterviewResult(
            Long id,
            InterviewResult result);
}

