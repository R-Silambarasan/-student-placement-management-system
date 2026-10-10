
package com.placement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.placement.constants.PlacementConstants;
import com.placement.dto.ApplicationRequestDto;
import com.placement.dto.ApplicationResponseDto;
import com.placement.entity.Application;
import com.placement.entity.JobDrive;
import com.placement.entity.Student;
import com.placement.enums.ApplicationStatus;
import com.placement.enums.JobDriveStatus;
import com.placement.exception.ApplicationAlreadyExistsException;
import com.placement.exception.ApplicationNotFoundException;
import com.placement.exception.JobDriveNotFoundException;
import com.placement.exception.StudentNotFoundException;
import com.placement.mapper.ApplicationMapper;
import com.placement.repository.ApplicationRepository;
import com.placement.repository.JobDriveRepository;
import com.placement.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JobDriveRepository jobDriveRepository;

    @Autowired
    private ApplicationMapper mapper;

    @Override
    @CacheEvict(value = {
    	    
    	    "applications",
    	    "applicationsByStatus",
    	    "applicationsByShortlisted",
    	    "applicationsByStudent",
    	    "applicationsByJobDrive",
    	    "applicationsByStatusAndShortlisted",
    	    "shortlistedApplications",
    	    "appliedApplications",
    	    "applicationsPages",
    	    "applicationsSorted",

    	    
    	    "offers",
    	    "offersByStatus",
    	    "offersByJobTitle",
    	    "offersByApplication",
    	    "offersByMinimumSalary",
    	    "offersByStatusAndJobTitle",
    	    "acceptedOffers",
    	    "offeredOffers",
    	    "offersByApplicationNative",
    	    "offersPages",
    	    "offersSorted"
    	}, allEntries = true)
    public ApplicationResponseDto createOrUpdate(
            ApplicationRequestDto requestDto) {

        log.info(
                "Application request received. Student ID: {}, Job Drive ID: {}",
                requestDto.getStudentId(),
                requestDto.getJobDriveId());

        Application application;

        Student student = studentRepository
                .findById(requestDto.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(
                        PlacementConstants.STUDENT_NOT_FOUND
                                + requestDto.getStudentId()));

        JobDrive jobDrive = jobDriveRepository
                .findById(requestDto.getJobDriveId())
                .orElseThrow(() -> new JobDriveNotFoundException(
                        PlacementConstants.JOB_DRIVE_NOT_FOUND
                                + requestDto.getJobDriveId()));

        if (requestDto.getId() == null) {

            if (jobDrive.getStatus() != JobDriveStatus.OPEN) {
                log.warn(
                        "Application rejected. Job drive is not open. Job Drive ID: {}",
                        jobDrive.getId());

                throw new IllegalStateException(
                        PlacementConstants.APPLICATION_CLOSED);
            }

            if (jobDrive.getApplicationDeadline() != null
                    && LocalDateTime.now().isAfter(
                            jobDrive.getApplicationDeadline())) {

                log.warn(
                        "Application rejected. Deadline passed. Job Drive ID: {}",
                        jobDrive.getId());

                throw new IllegalStateException(
                        PlacementConstants.APPLICATION_DEADLINE_PASSED);
            }

            Optional<Application> existingApplication =
                    applicationRepository.findByStudentIdAndJobDriveId(
                            requestDto.getStudentId(),
                            requestDto.getJobDriveId());

            if (existingApplication.isPresent()) {
                log.warn(
                        "Duplicate application. Student ID: {}, Job Drive ID: {}",
                        requestDto.getStudentId(),
                        requestDto.getJobDriveId());

                throw new ApplicationAlreadyExistsException(
                        PlacementConstants.APPLICATION_ALREADY_EXISTS);
            }
            application = mapper.toEntity(requestDto);
            application.setStudent(student);
            application.setJobDrive(jobDrive);
            application.setAppliedAt(LocalDateTime.now());

            application.setStatus(ApplicationStatus.APPLIED);
            application.setShortlisted(false);

            log.info(
                    "Creating application. Student ID: {}, Job Drive ID: {}",
                    student.getId(),
                    jobDrive.getId());

        } else {

            application = applicationRepository
                    .findById(requestDto.getId())
                    .orElseThrow(() -> new ApplicationNotFoundException(
                            PlacementConstants.APPLICATION_NOT_FOUND
                                    + requestDto.getId()));

            mapper.updateEntity(requestDto, application);
            application.setStudent(student);
            application.setJobDrive(jobDrive);

            log.info(
                    "Updating application. ID: {}",
                    requestDto.getId());
        }

        Application savedApplication =
                applicationRepository.save(application);

        log.info(
                "Application saved successfully. Application ID: {}",
                savedApplication.getId());

        return mapper.toResponseDto(savedApplication);
    }

    @Override
    @Cacheable(value = "applications", key = "#id")
    public ApplicationResponseDto getById(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        PlacementConstants.APPLICATION_NOT_FOUND + id));

        return mapper.toResponseDto(application);
    }

    @Override
    @Cacheable(value = "applications", key = "'all'")
    public List<ApplicationResponseDto> getAll() {

        return applicationRepository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {
    	   
    	    "applications",
    	    "applicationsByStatus",
    	    "applicationsByShortlisted",
    	    "applicationsByStudent",
    	    "applicationsByJobDrive",
    	    "applicationsByStatusAndShortlisted",
    	    "shortlistedApplications",
    	    "appliedApplications",
    	    "applicationsPages",
    	    "applicationsSorted",

    	    
    	    "offers",
    	    "offersByStatus",
    	    "offersByJobTitle",
    	    "offersByApplication",
    	    "offersByMinimumSalary",
    	    "offersByStatusAndJobTitle",
    	    "acceptedOffers",
    	    "offeredOffers",
    	    "offersByApplicationNative",
    	    "offersPages",
    	    "offersSorted"
    	}, allEntries = true)
    public void deleteById(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        PlacementConstants.APPLICATION_NOT_FOUND + id));

        applicationRepository.delete(application);

        log.info(
                "{}. ID: {}",
                PlacementConstants.APPLICATION_DELETED,
                id);
    }

    @Override
    @Cacheable(value = "applicationsByStatus", key = "#status")
    public List<ApplicationResponseDto> getByStatus(
            ApplicationStatus status) {

        return applicationRepository.findByStatus(status)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "applicationsByShortlisted", key = "#shortlisted")
    public List<ApplicationResponseDto> getByShortlisted(
            Boolean shortlisted) {

        return applicationRepository.findByShortlisted(shortlisted)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "applicationsByStudent", key = "#studentId")
    public List<ApplicationResponseDto> getByStudentId(Long studentId) {

        return applicationRepository.findByStudentId(studentId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "applicationsByJobDrive", key = "#jobDriveId")
    public List<ApplicationResponseDto> getByJobDriveId(Long jobDriveId) {

        return applicationRepository.findByJobDriveId(jobDriveId)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "applicationsByStatusAndShortlisted",
            key = "#status.name() + '-' + #shortlisted")
    public List<ApplicationResponseDto> getByStatusAndShortlisted(
            ApplicationStatus status, Boolean shortlisted) {

        return applicationRepository.findByStatusAndShortlisted(
                        status, shortlisted)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "shortlistedApplications", key = "'all'")
    public List<ApplicationResponseDto> getShortlistedApplications() {

        return applicationRepository.findShortlistedApplications()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "appliedApplications", key = "'all'")
    public List<ApplicationResponseDto> getAppliedApplications() {

        return applicationRepository.findAppliedApplications()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "applicationsPages",
            key = "#pageNo + '-' + #pageSize")
    public Page<ApplicationResponseDto> getApplications(
            int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Application> applications =
                applicationRepository.findAll(pageable);

        return applications.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(
            value = "applicationsSorted",
            key = "#pageNo + '-' + #pageSize + '-' + #field")
    public Page<ApplicationResponseDto> getApplicationsSorted(
            int pageNo, int pageSize, String field) {

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(field).ascending());

        Page<Application> applications =
                applicationRepository.findAll(pageable);

        return applications.map(mapper::toResponseDto);
    }

    @Override
    @CacheEvict(value = {
    	    
    	    "applications",
    	    "applicationsByStatus",
    	    "applicationsByShortlisted",
    	    "applicationsByStudent",
    	    "applicationsByJobDrive",
    	    "applicationsByStatusAndShortlisted",
    	    "shortlistedApplications",
    	    "appliedApplications",
    	    "applicationsPages",
    	    "applicationsSorted",

    	   
    	    "offers",
    	    "offersByStatus",
    	    "offersByJobTitle",
    	    "offersByApplication",
    	    "offersByMinimumSalary",
    	    "offersByStatusAndJobTitle",
    	    "acceptedOffers",
    	    "offeredOffers",
    	    "offersByApplicationNative",
    	    "offersPages",
    	    "offersSorted"
    	}, allEntries = true)
    public void shortlistApplication(Long id) {

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        PlacementConstants.APPLICATION_NOT_FOUND + id));

        application.setShortlisted(true);
        application.setStatus(ApplicationStatus.SHORTLISTED);

        applicationRepository.save(application);

        log.info(
                "{}. ID: {}",
                PlacementConstants.APPLICATION_SHORTLISTED,
                id);
    }
}

