package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.StudentRequestDto;
import com.placement.dto.StudentResponseDto;

public interface StudentService {

    StudentResponseDto createOrUpdate(StudentRequestDto requestDto);

    StudentResponseDto getById(Long id);

    List<StudentResponseDto> getAll();

    void deleteById(Long id);

    StudentResponseDto getByEmail(String email);

    List<StudentResponseDto> getByDepartment(String department);

    List<StudentResponseDto> getByCgpa(Double cgpa);

    List<StudentResponseDto> getStudentsWithBacklogs();

    List<StudentResponseDto> getByDepartmentAndCgpa(
            String department, Double cgpa);
    
    
    Page<StudentResponseDto> getStudents(
            int pageNo,
            int pageSize);

    Page<StudentResponseDto> getStudentsSorted(
            int pageNo,
            int pageSize,
            String field);
    
    List<StudentResponseDto> getEligibleStudents(
            Double cgpa,
            Integer backlogs,
            String department);
}