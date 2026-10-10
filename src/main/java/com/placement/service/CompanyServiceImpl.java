
package com.placement.service;

import java.util.List;
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
import com.placement.dto.CompanyRequestDto;
import com.placement.dto.CompanyResponseDto;
import com.placement.entity.Company;
import com.placement.exception.CompanyNotFoundException;
import com.placement.mapper.CompanyMapper;
import com.placement.repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper mapper;

    @Override
    @CacheEvict(value = {
    	   
    	    "companies",
    	    "companiesByEmail",
    	    "companiesByIndustry",
    	    "companiesByLocation",
    	    "companiesByName",
    	    "companiesByIndustryAndLocation",
    	    "companiesPages",
    	    "companiesSorted",

    	    
    	    "jobDrives",
    	    "jobDrivesByStatus",
    	    "jobDrivesByDepartment",
    	    "jobDrivesByMinimumCgpa",
    	    "jobDrivesByLocation",
    	    "jobDrivesByStatusAndDepartment",
    	    "jobDrivesPages",
    	    "jobDrivesSorted",
    	    "eligibleStudents"
    	}, allEntries = true)
    public CompanyResponseDto createOrUpdate(
            CompanyRequestDto requestDto) {

        log.info("Company request received. ID: {}",
                requestDto.getId());

        Company company;

        if (requestDto.getId() == null) {

           
            log.info("Creating company: {}",
                    requestDto.getName());

            company = mapper.toEntity(requestDto);

        } else {

            log.info("Updating company. ID: {}",
                    requestDto.getId());

            company = companyRepository
                    .findById(requestDto.getId())
                    .orElseThrow(() -> {
                        log.warn("Company not found. ID: {}",
                                requestDto.getId());

                        return new CompanyNotFoundException(
                                PlacementConstants.COMPANY_NOT_FOUND
                                        + requestDto.getId());
                    });

            mapper.updateEntity(requestDto, company);
        }

        Company savedCompany =
                companyRepository.save(company);

        log.info("Company saved successfully. ID: {}",
                savedCompany.getId());

        return mapper.toResponseDto(savedCompany);
    }

    @Override
    @Cacheable(value = "companies", key = "#id")
    public CompanyResponseDto getById(Long id) {

        log.info("Fetching company. ID: {}", id);

        Company company = companyRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Company not found. ID: {}", id);

                    return new CompanyNotFoundException(
                            PlacementConstants.COMPANY_NOT_FOUND + id);
                });

        return mapper.toResponseDto(company);
    }

    @Override
    @Cacheable(value = "companies", key = "'all'")
    public List<CompanyResponseDto> getAll() {

        log.info("Fetching all companies");

        return companyRepository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {
    	    
    	    "companies",
    	    "companiesByEmail",
    	    "companiesByIndustry",
    	    "companiesByLocation",
    	    "companiesByName",
    	    "companiesByIndustryAndLocation",
    	    "companiesPages",
    	    "companiesSorted",

    	    
    	    "jobDrives",
    	    "jobDrivesByStatus",
    	    "jobDrivesByDepartment",
    	    "jobDrivesByMinimumCgpa",
    	    "jobDrivesByLocation",
    	    "jobDrivesByStatusAndDepartment",
    	    "jobDrivesPages",
    	    "jobDrivesSorted",
    	    "eligibleStudents"
    	}, allEntries = true)
    public void deleteById(Long id) {

        log.info("Deleting company. ID: {}", id);

        Company company = companyRepository
                .findById(id)
                .orElseThrow(() -> {
                    log.warn("Company not found. ID: {}", id);

                    return new CompanyNotFoundException(
                            PlacementConstants.COMPANY_NOT_FOUND + id);
                });

        companyRepository.delete(company);

        log.info("Company deleted successfully. ID: {}", id);
    }

    @Override
    @Cacheable(value = "companiesByEmail", key = "#email")
    public CompanyResponseDto getByEmail(String email) {

        log.info("Fetching company by email: {}", email);

        Company company = companyRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Company not found with email: {}", email);

                    return new CompanyNotFoundException(
                            PlacementConstants.COMPANY_NOT_FOUND_WITH_EMAIL
                                    + email);
                });

        return mapper.toResponseDto(company);
    }

    @Override
    @Cacheable(value = "companiesByIndustry", key = "#industry")
    public List<CompanyResponseDto> getByIndustry(String industry) {

        log.info("Fetching companies by industry: {}", industry);

        return companyRepository.findByIndustry(industry)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "companiesByLocation", key = "#location")
    public List<CompanyResponseDto> getByLocation(String location) {

        log.info("Fetching companies by location: {}", location);

        return companyRepository.findByLocation(location)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "companiesByName", key = "#name")
    public List<CompanyResponseDto> getByName(String name) {

        log.info("Searching company by name: {}", name);

        return companyRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "companiesByIndustryAndLocation",
            key = "#industry + '-' + #location")
    public List<CompanyResponseDto> getByIndustryAndLocation(
            String industry, String location) {

        log.info(
                "Fetching companies by industry: {} and location: {}",
                industry, location);

        return companyRepository.findByIndustryAndLocation(
                        industry, location)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(
            value = "companiesPages",
            key = "#pageNo + '-' + #pageSize")
    public Page<CompanyResponseDto> getCompanies(
            int pageNo, int pageSize) {

        log.info(
                "Fetching companies with pagination. Page: {}, Size: {}",
                pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Company> companies =
                companyRepository.findAll(pageable);

        return companies.map(mapper::toResponseDto);
    }

    @Override
    @Cacheable(
            value = "companiesSorted",
            key = "#pageNo + '-' + #pageSize + '-' + #field")
    public Page<CompanyResponseDto> getCompaniesSorted(
            int pageNo, int pageSize, String field) {

        log.info(
                "Fetching companies with sorting. Page: {}, Size: {}, Field: {}",
                pageNo, pageSize, field);

        Pageable pageable = PageRequest.of(
                pageNo, pageSize, Sort.by(field).ascending());

        Page<Company> companies =
                companyRepository.findAll(pageable);

        return companies.map(mapper::toResponseDto);
    }
}

