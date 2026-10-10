
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
import com.placement.dto.JobDriveRequestDto;
import com.placement.dto.JobDriveResponseDto;
import com.placement.dto.StudentResponseDto;
import com.placement.entity.Company;
import com.placement.entity.JobDrive;
import com.placement.enums.JobDriveStatus;
import com.placement.exception.CompanyNotFoundException;
import com.placement.exception.JobDriveNotFoundException;
import com.placement.mapper.JobDriveMapper;
import com.placement.mapper.StudentMapper;
import com.placement.repository.CompanyRepository;
import com.placement.repository.JobDriveRepository;
import com.placement.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JobDriveServiceImpl implements JobDriveService {

    @Autowired
    private JobDriveRepository jobDriveRepository;

    @Autowired
    private JobDriveMapper mapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    @CacheEvict(value = {
    	    
    	    "jobDrives",
    	    "jobDrivesByStatus",
    	    "jobDrivesByDepartment",
    	    "jobDrivesByMinimumCgpa",
    	    "jobDrivesByLocation",
    	    "jobDrivesByStatusAndDepartment",
    	    "jobDrivesPages",
    	    "jobDrivesSorted",
    	    "eligibleStudents",

    
    	    "applications",
    	    "applicationsByStatus",
    	    "applicationsByShortlisted",
    	    "applicationsByStudent",
    	    "applicationsByJobDrive",
    	    "applicationsByStatusAndShortlisted",
    	    "shortlistedApplications",
    	    "appliedApplications",
    	    "applicationsPages",
    	    "applicationsSorted"
    	}, allEntries = true)
    public JobDriveResponseDto createOrUpdate(
            JobDriveRequestDto requestDto) {

        log.info(
                "Job drive request received. ID: {}, Company ID: {}",
                requestDto.getId(),
                requestDto.getCompanyId());

        JobDrive jobDrive;

        Company company = companyRepository
                .findById(requestDto.getCompanyId())
                .orElseThrow(() -> {
                    log.warn(
                            "Company not found. ID: {}",
                            requestDto.getCompanyId());

                    return new CompanyNotFoundException(
                            PlacementConstants.COMPANY_NOT_FOUND
                                    + requestDto.getCompanyId());
                });

        if (requestDto.getId() == null) {

           
            log.info(
                    "Creating job drive: {}",
                    requestDto.getJobTitle());

            jobDrive = mapper.toEntity(requestDto);
            jobDrive.setCompany(company);

        } else {

            
            log.info(
                    "Updating job drive. ID: {}",
                    requestDto.getId());

            jobDrive = jobDriveRepository
                    .findById(requestDto.getId())
                    .orElseThrow(() -> {
                        log.warn(
                                "Job drive not found. ID: {}",
                                requestDto.getId());

                        return new JobDriveNotFoundException(
                                PlacementConstants.JOB_DRIVE_NOT_FOUND
                                        + requestDto.getId());
                    });

            mapper.updateEntity(requestDto, jobDrive);
            jobDrive.setCompany(company);
        }

        JobDrive savedJobDrive =
                jobDriveRepository.save(jobDrive);

        log.info(
                "Job drive saved successfully. ID: {}",
                savedJobDrive.getId());

        return mapper.toResponseDto(savedJobDrive);
    }

    @Override
    @Cacheable(value = "jobDrives", key = "#id")
    public JobDriveResponseDto getById(Long id) {

        log.info("Fetching job drive. ID: {}", id);

        JobDrive jobDrive = jobDriveRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Job drive not found. ID: {}", id);

                    return new JobDriveNotFoundException(
                            PlacementConstants.JOB_DRIVE_NOT_FOUND + id);
                });

        return mapper.toResponseDto(jobDrive);
    }

    @Override
    @Cacheable(value = "jobDrives", key = "'all'")
    public List<JobDriveResponseDto> getAll() {

        log.info("Fetching all job drives");

        List<JobDrive> jobDrives =
                jobDriveRepository.findAll();

        return jobDrives.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {
    	    
    	    "jobDrives",
    	    "jobDrivesByStatus",
    	    "jobDrivesByDepartment",
    	    "jobDrivesByMinimumCgpa",
    	    "jobDrivesByLocation",
    	    "jobDrivesByStatusAndDepartment",
    	    "jobDrivesPages",
    	    "jobDrivesSorted",
    	    "eligibleStudents",

    	    
    	    "applications",
    	    "applicationsByStatus",
    	    "applicationsByShortlisted",
    	    "applicationsByStudent",
    	    "applicationsByJobDrive",
    	    "applicationsByStatusAndShortlisted",
    	    "shortlistedApplications",
    	    "appliedApplications",
    	    "applicationsPages",
    	    "applicationsSorted"
    	}, allEntries = true)
    public void deleteById(Long id) {

        log.info("Deleting job drive. ID: {}", id);

        JobDrive jobDrive = jobDriveRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Job drive not found. ID: {}", id);

                    return new JobDriveNotFoundException(
                            PlacementConstants.JOB_DRIVE_NOT_FOUND + id);
                });

        jobDriveRepository.delete(jobDrive);

        log.info("Job drive deleted successfully. ID: {}", id);
    }

    @Override
    @Cacheable(value = "jobDrivesByStatus", key = "#status")
    public List<JobDriveResponseDto> getByStatus(
            JobDriveStatus status) {

        log.info("Fetching job drives by status: {}", status);

        return jobDriveRepository
                .findByStatus(status)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "jobDrivesByDepartment", key = "#department")
    public List<JobDriveResponseDto> getByEligibleDepartment(
            String department) {

        log.info(
                "Fetching job drives by eligible department: {}",
                department);

        return jobDriveRepository
                .findByEligibleDepartment(department)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "jobDrivesByMinimumCgpa", key = "#cgpa")
    public List<JobDriveResponseDto> getByMinimumCgpa(
            Double cgpa) {

        log.info(
                "Fetching job drives with minimum CGPA: {}",
                cgpa);

        return jobDriveRepository
                .findByMinimumCgpaGreaterThanEqual(cgpa)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "jobDrivesByLocation", key = "#location")
    public List<JobDriveResponseDto> getByLocation(
            String location) {

        log.info("Fetching job drives by location: {}", location);

        return jobDriveRepository
                .findByLocation(location)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
	
    public List<JobDriveResponseDto> getByStatusAndDepartment(
            JobDriveStatus status,
            String department) {

        log.info(
                "Fetching job drives by status: {} and department: {}",
                status,
                department);

        return jobDriveRepository
                .findByStatusAndEligibleDepartment(
                        status,
                        department)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "jobDrivesPages",
            key = "#pageNo + '-' + #pageSize")
    public Page<JobDriveResponseDto> getJobDrives(
            int pageNo,
            int pageSize) {

        log.info(
                "Fetching job drives with pagination. Page: {}, Size: {}",
                pageNo,
                pageSize);

        Pageable pageable =
                PageRequest.of(pageNo, pageSize);

        Page<JobDrive> jobDrives =
                jobDriveRepository.findAll(pageable);

        return jobDrives.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(
            value = "jobDrivesSorted",
            key = "#pageNo + '-' + #pageSize + '-' + #field")
    public Page<JobDriveResponseDto> getJobDrivesSorted(
            int pageNo,
            int pageSize,
            String field) {

        log.info(
                "Fetching job drives with sorting. Page: {}, Size: {}, Field: {}",
                pageNo,
                pageSize,
                field);

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(field).ascending());

        Page<JobDrive> jobDrives =
                jobDriveRepository.findAll(pageable);

        return jobDrives.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(value = "eligibleStudents", key = "#jobDriveId")
    public List<StudentResponseDto> getEligibleStudents(
            Long jobDriveId) {

        log.info(
                "Finding eligible students for Job Drive ID: {}",
                jobDriveId);

        JobDrive jobDrive = jobDriveRepository
                .findById(jobDriveId)
                .orElseThrow(() -> {
                    log.warn(
                            "Job drive not found while finding eligible students. ID: {}",
                            jobDriveId);

                    return new JobDriveNotFoundException(
                            PlacementConstants.JOB_DRIVE_NOT_FOUND
                                    + jobDriveId);
                });

        List<StudentResponseDto> eligibleStudents =
                studentRepository
                        .findEligibleStudents(
                                jobDrive.getMinimumCgpa(),
                                jobDrive.getMaximumBacklogs(),
                                jobDrive.getEligibleDepartment())
                        .stream()
                        .map(studentMapper::toResponseDto)
                        .toList();

        log.info(
                "Eligible students found for Job Drive ID: {}. Count: {}",
                jobDriveId,
                eligibleStudents.size());

        return eligibleStudents;
    }
}

