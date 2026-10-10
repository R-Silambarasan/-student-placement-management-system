
package com.placement.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.placement.dto.OfferRequestDto;
import com.placement.dto.OfferResponseDto;
import com.placement.enums.OfferStatus;

public interface OfferService {

    OfferResponseDto createOrUpdate(OfferRequestDto requestDto);

    OfferResponseDto getById(Long id);

    List<OfferResponseDto> getAll();

    void deleteById(Long id);

    
    List<OfferResponseDto> getByStatus(OfferStatus status);

    List<OfferResponseDto> getByJobTitle(String jobTitle);

    List<OfferResponseDto> getByApplicationId(Long applicationId);

    List<OfferResponseDto> getByMinimumSalary(Double salary);

    
    List<OfferResponseDto> getByStatusAndJobTitle(
            OfferStatus status,
            String jobTitle);

    List<OfferResponseDto> getAcceptedOffers();

    List<OfferResponseDto> getOfferedOffers();

    List<OfferResponseDto> getOffersByApplicationNative(
            Long applicationId);

    Page<OfferResponseDto> getPagination(int pageNo, int pageSize);

    Page<OfferResponseDto> getSorting(
            int pageNo,
            int pageSize,
            String field);
}

