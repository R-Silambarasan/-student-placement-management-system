
package com.placement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.placement.constants.PlacementConstants;
import com.placement.dto.OfferRequestDto;
import com.placement.dto.OfferResponseDto;
import com.placement.entity.Application;
import com.placement.entity.Offer;
import com.placement.enums.ApplicationStatus;
import com.placement.enums.OfferStatus;
import com.placement.exception.ApplicationNotFoundException;
import com.placement.exception.OfferNotFoundException;
import com.placement.mapper.OfferMapper;
import com.placement.repository.ApplicationRepository;
import com.placement.repository.OfferRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private OfferMapper mapper;

   
    @Override
    @Transactional
    @CacheEvict(value = {
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
            "offersSorted",
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
    public OfferResponseDto createOrUpdate(
            OfferRequestDto requestDto) {

        log.info(
                "Offer request received. ID: {}, Application ID: {}",
                requestDto.getId(),
                requestDto.getApplicationId());

        Application application = applicationRepository
                .findById(requestDto.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException(
                        PlacementConstants.APPLICATION_NOT_FOUND
                                + requestDto.getApplicationId()));

        Offer offer;

        if (requestDto.getId() == null) {

            
            log.info(
                    "Creating offer for Application ID: {}",
                    requestDto.getApplicationId());

            offer = mapper.toEntity(requestDto);
            offer.setApplication(application);

            
            if (requestDto.getStatus() == OfferStatus.RELEASED) {
                application.setStatus(ApplicationStatus.OFFERED);
            }

        } else {

           
            log.info("Updating offer. ID: {}", requestDto.getId());

            offer = offerRepository.findById(requestDto.getId())
                    .orElseThrow(() -> new OfferNotFoundException(
                            PlacementConstants.OFFER_NOT_FOUND
                                    + requestDto.getId()));

            mapper.updateEntity(requestDto, offer);
            offer.setApplication(application);

           
            if (requestDto.getStatus() == OfferStatus.RELEASED) {
                application.setStatus(ApplicationStatus.OFFERED);

            } else if (requestDto.getStatus() == OfferStatus.ACCEPTED) {
                application.setStatus(ApplicationStatus.HIRED);
            }
        }

        applicationRepository.save(application);

        Offer savedOffer = offerRepository.save(offer);

        log.info(
                "Offer saved successfully. Offer ID: {}",
                savedOffer.getId());

        return mapper.toResponseDto(savedOffer);
    }

  
    @Override
    @Cacheable(value = "offers", key = "#id")
    public OfferResponseDto getById(Long id) {

        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(
                        PlacementConstants.OFFER_NOT_FOUND + id));

        return mapper.toResponseDto(offer);
    }

   
    @Override
    @Cacheable(value = "offers", key = "'all'")
    public List<OfferResponseDto> getAll() {

        return offerRepository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }


    @Override
    @CacheEvict(value = {
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

        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException(
                        PlacementConstants.OFFER_NOT_FOUND + id));

        offerRepository.delete(offer);

        log.info("Offer deleted successfully. ID: {}", id);
    }

    
    @Override
    @Cacheable(value = "offersByStatus", key = "#status")
    public List<OfferResponseDto> getByStatus(OfferStatus status) {

        return offerRepository.findByStatus(status)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

   
    @Override
    @Cacheable(value = "offersByJobTitle", key = "#jobTitle")
    public List<OfferResponseDto> getByJobTitle(String jobTitle) {

        return offerRepository.findByJobTitle(jobTitle)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

   
    @Override
    @Cacheable(value = "offersByApplication", key = "#applicationId")
    public List<OfferResponseDto> getByApplicationId(
            Long applicationId) {

        return offerRepository.findByApplicationId(applicationId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

   
    @Override
    @Cacheable(value = "offersByMinimumSalary", key = "#salary")
    public List<OfferResponseDto> getByMinimumSalary(Double salary) {

        return offerRepository.findBySalaryGreaterThanEqual(salary)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

  
    @Override
    @Cacheable(
            value = "offersByStatusAndJobTitle",
            key = "#status.name() + '-' + #jobTitle")
    public List<OfferResponseDto> getByStatusAndJobTitle(
            OfferStatus status,
            String jobTitle) {

        return offerRepository
                .findByStatusAndJobTitle(status, jobTitle)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

   
    @Override
    @Cacheable(value = "acceptedOffers", key = "'all'")
    public List<OfferResponseDto> getAcceptedOffers() {

        return offerRepository
                .findOffersByStatus(OfferStatus.ACCEPTED)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    // 11. GET RELEASED OFFERS
    @Override
    @Cacheable(value = "offeredOffers", key = "'all'")
    public List<OfferResponseDto> getOfferedOffers() {

        return offerRepository.findReleasedOffers()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    
    @Override
    @Cacheable(
            value = "offersByApplicationNative",
            key = "#applicationId")
    public List<OfferResponseDto> getOffersByApplicationNative(
            Long applicationId) {

        return offerRepository
                .findOffersByApplicationNative(applicationId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }

   
    @Override
    @Cacheable(
            value = "offersPages",
            key = "#pageNo + '-' + #pageSize")
    public Page<OfferResponseDto> getPagination(
            int pageNo,
            int pageSize) {

        PageRequest pageRequest =
                PageRequest.of(pageNo, pageSize);

        return offerRepository.findAll(pageRequest)
                .map(mapper::toResponseDto);
    }

  
    @Override
    @Cacheable(
            value = "offersSorted",
            key = "#pageNo + '-' + #pageSize + '-' + #field")
    public Page<OfferResponseDto> getSorting(
            int pageNo,
            int pageSize,
            String field) {

        PageRequest pageRequest = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(field).ascending());

        return offerRepository.findAll(pageRequest)
                .map(mapper::toResponseDto);
    }
}
