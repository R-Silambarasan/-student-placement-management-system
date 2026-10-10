package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.JobDriveRequestDto;
import com.placement.dto.JobDriveResponseDto;
import com.placement.dto.StudentResponseDto;
import com.placement.enums.JobDriveStatus;

public interface JobDriveService {

    JobDriveResponseDto createOrUpdate(
            JobDriveRequestDto requestDto);

    JobDriveResponseDto getById(Long id);

    List<JobDriveResponseDto> getAll();

    void deleteById(Long id);
    
 
    List<JobDriveResponseDto> getByStatus(
            JobDriveStatus status);

    List<JobDriveResponseDto> getByEligibleDepartment(
            String department);

    List<JobDriveResponseDto> getByMinimumCgpa(
            Double cgpa);

    List<JobDriveResponseDto> getByLocation(
            String location);

    List<JobDriveResponseDto> getByStatusAndDepartment(
            JobDriveStatus status,
            String department);
    
    
    Page<JobDriveResponseDto> getJobDrives(
            int pageNo,
            int pageSize);

    Page<JobDriveResponseDto> getJobDrivesSorted(
            int pageNo,
            int pageSize,
            String field);
    
    List<StudentResponseDto> getEligibleStudents(Long jobDriveId);
}