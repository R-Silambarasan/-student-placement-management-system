package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.CompanyRequestDto;
import com.placement.dto.CompanyResponseDto;

public interface CompanyService {

    CompanyResponseDto createOrUpdate(
            CompanyRequestDto requestDto);

    CompanyResponseDto getById(Long id);

    List<CompanyResponseDto> getAll();

    void deleteById(Long id);

    
    CompanyResponseDto getByEmail(String email);

    List<CompanyResponseDto> getByIndustry(
            String industry);

    List<CompanyResponseDto> getByLocation(
            String location);

    List<CompanyResponseDto> getByName(
            String name);

    List<CompanyResponseDto> getByIndustryAndLocation(
            String industry,
            String location);
    
    public Page<CompanyResponseDto> getCompanies(
            int pageNo,
            int pageSize);
    
    Page<CompanyResponseDto> getCompaniesSorted(
            int pageNo,
            int pageSize,
            String field);
}