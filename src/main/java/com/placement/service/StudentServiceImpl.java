
package com.placement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.placement.constants.PlacementConstants;
import com.placement.dto.StudentRequestDto;
import com.placement.dto.StudentResponseDto;
import com.placement.entity.Student;
import com.placement.exception.StudentNotFoundException;
import com.placement.mapper.StudentMapper;
import com.placement.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper mapper;

    @Override
    @CacheEvict(value = { 
    		"students", "studentsByEmail", "studentsByDepartment", "studentsByCgpa", "studentsWithBacklogs", "studentsByDepartmentAndCgpa", "studentsPages", "studentsSorted", 

 
    		"eligibleStudents", "jobDrives", "jobDrivesByStatus", "jobDrivesByDepartment", "jobDrivesByMinimumCgpa", "jobDrivesByLocation", "jobDrivesByStatusAndDepartment", "jobDrivesPages", "jobDrivesSorted", 


    		"applications", "applicationsByStatus", "applicationsByShortlisted", "applicationsByStudent", "applicationsByJobDrive", "applicationsByStatusAndShortlisted", "shortlistedApplications", "appliedApplications", "applicationsPages", "applicationsSorted" }, allEntries = true)
    
    public StudentResponseDto createOrUpdate(
            StudentRequestDto requestDto) {

        Student student;

        if (requestDto.getId() == null) {



            log.info("Creating new student with email: {}",
                    requestDto.getEmail());

            student = mapper.toEntity(requestDto);

        } else {



            log.info("Updating student with id: {}",
                    requestDto.getId());

            student = studentRepository
                    .findById(requestDto.getId())
                    .orElseThrow(() -> {
                        log.warn("Student not found with id: {}",
                                requestDto.getId());

                        return new StudentNotFoundException(
                                PlacementConstants.STUDENT_NOT_FOUND
                                        + requestDto.getId());
                    });

            mapper.updateEntity(requestDto, student);
        }

        Student savedStudent =
                studentRepository.save(student);

        log.info("Student saved successfully with id: {}",
                savedStudent.getId());

        return mapper.toResponseDto(savedStudent);
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public StudentResponseDto getById(Long id) {

        log.info("Fetching student with id: {}", id);

        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Student not found with id: {}", id);

                    return new StudentNotFoundException(
                            PlacementConstants.STUDENT_NOT_FOUND + id);
                });

        return mapper.toResponseDto(student);
    }

    @Override
    @Cacheable(value = "students", key = "'all'")
    public List<StudentResponseDto> getAll() {

        log.info("Fetching all students");

        List<Student> students =
                studentRepository.findAll();

        log.info("Total students found: {}", students.size());

        return students.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = { 
    		"students", "studentsByEmail", "studentsByDepartment", "studentsByCgpa", "studentsWithBacklogs", "studentsByDepartmentAndCgpa", "studentsPages", "studentsSorted", 
    		
    		"eligibleStudents", "jobDrives", "jobDrivesByStatus", "jobDrivesByDepartment", "jobDrivesByMinimumCgpa", "jobDrivesByLocation", "jobDrivesByStatusAndDepartment", "jobDrivesPages", "jobDrivesSorted", 
    		
    		"applications", "applicationsByStatus", "applicationsByShortlisted", "applicationsByStudent", "applicationsByJobDrive", "applicationsByStatusAndShortlisted", "shortlistedApplications", "appliedApplications", "applicationsPages", "applicationsSorted" }, allEntries = true)
    
    public void deleteById(Long id) {

        log.info("Deleting student with id: {}", id);

        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Student not found with id: {}", id);

                    return new StudentNotFoundException(
                            PlacementConstants.STUDENT_NOT_FOUND + id);
                });

        studentRepository.delete(student);

        log.info("Student deleted successfully with id: {}", id);
    }



    @Override
    @Cacheable(value = "studentsByEmail", key = "#email")
    public StudentResponseDto getByEmail(String email) {

        log.info("Searching student by email: {}", email);

        Student student = studentRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Student not found with email: {}", email);

                    return new StudentNotFoundException(
                            PlacementConstants.STUDENT_NOT_FOUND_WITH_EMAIL
                                    + email);
                });

        return mapper.toResponseDto(student);
    }

    
    @Override
    @Cacheable(value = "studentsByDepartment", key = "#department")
    public List<StudentResponseDto> getByDepartment(
            String department) {

        log.info("Searching students by department: {}",
                department);

        return studentRepository
                .findByDepartment(department)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    
    @Override
    @Cacheable(value = "studentsByCgpa", key = "#cgpa")
    public List<StudentResponseDto> getByCgpa(
            Double cgpa) {

        log.info("Searching students with CGPA >= {}",
                cgpa);

        return studentRepository
                .findByCgpaGreaterThanEqual(cgpa)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    
    @Override
    @Cacheable(value = "studentsWithBacklogs", key = "'all'")
    public List<StudentResponseDto> getStudentsWithBacklogs() {

        log.info("Fetching students with backlogs");

        return studentRepository
                .findStudentsWithBacklogs()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    
    
    @Override
    @Cacheable(
            value = "studentsByDepartmentAndCgpa",
            key = "#department + '-' + #cgpa")
    public List<StudentResponseDto> getByDepartmentAndCgpa(
            String department,
            Double cgpa) {

        log.info(
                "Searching students by department: {} and CGPA >= {}",
                department,
                cgpa);

        return studentRepository
                .findByDepartmentAndCgpaGreaterThanEqual(
                        department,
                        cgpa)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "studentsPages",
            key = "#pageNo + '-' + #pageSize")
    public Page<StudentResponseDto> getStudents(
            int pageNo,
            int pageSize) {

        log.info(
                "Fetching students with pagination. Page: {}, Size: {}",
                pageNo,
                pageSize);

        Pageable pageable =
                PageRequest.of(pageNo, pageSize);

        Page<Student> students =
                studentRepository.findAll(pageable);

        return students.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(
            value = "studentsSorted",
            key = "#pageNo + '-' + #pageSize + '-' + #field")
    public Page<StudentResponseDto> getStudentsSorted(
            int pageNo,
            int pageSize,
            String field) {

        log.info(
                "Fetching students sorted by field: {}",
                field);

        Pageable pageable =
                PageRequest.of(
                        pageNo,
                        pageSize,
                        Sort.by(field).ascending());

        Page<Student> students =
                studentRepository.findAll(pageable);

        return students.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(
            value = "eligibleStudents",
            key = "#cgpa + '-' + #backlogs + '-' + #department")
    public List<StudentResponseDto> getEligibleStudents(
            Double cgpa,
            Integer backlogs,
            String department) {

        log.info(
                "Finding eligible students. CGPA >= {}, Backlogs <= {}, Department: {}",
                cgpa,
                backlogs,
                department);

        List<StudentResponseDto> students =
                studentRepository
                        .findEligibleStudents(
                                cgpa,
                                backlogs,
                                department)
                        .stream()
                        .map(mapper::toResponseDto)
                        .toList();

        log.info("Eligible students found: {}",
                students.size());

        return students;
    }
}

