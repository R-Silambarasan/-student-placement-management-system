
package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.ApplicationRequestDto;
import com.placement.dto.ApplicationResponseDto;
import com.placement.enums.ApplicationStatus;

public interface ApplicationService {

    ApplicationResponseDto createOrUpdate(
            ApplicationRequestDto requestDto);

    ApplicationResponseDto getById(Long id);

    List<ApplicationResponseDto> getAll();

    void deleteById(Long id);

    List<ApplicationResponseDto> getByStatus(
            ApplicationStatus status);

    List<ApplicationResponseDto> getByShortlisted(
            Boolean shortlisted);

    List<ApplicationResponseDto> getByStudentId(
            Long studentId);

    List<ApplicationResponseDto> getByJobDriveId(
            Long jobDriveId);

    List<ApplicationResponseDto> getByStatusAndShortlisted(
            ApplicationStatus status,
            Boolean shortlisted);

    List<ApplicationResponseDto> getShortlistedApplications();

    List<ApplicationResponseDto> getAppliedApplications();

    Page<ApplicationResponseDto> getApplications(
            int pageNo,
            int pageSize);

    Page<ApplicationResponseDto> getApplicationsSorted(
            int pageNo,
            int pageSize,
            String field);

    void shortlistApplication(Long id);
}