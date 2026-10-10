
package com.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placement.dto.OfferRequestDto;
import com.placement.dto.OfferResponseDto;
import com.placement.enums.OfferStatus;
import com.placement.service.OfferService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    
    @PostMapping
    @Operation(summary = "Create or update an offer")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Offer created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid offer details")
    })
    public ResponseEntity<OfferResponseDto> createOrUpdate(
            @Valid @RequestBody OfferRequestDto requestDto) {

        return ResponseEntity.ok(
                offerService.createOrUpdate(requestDto));
    }

    
    @GetMapping("/{id}")
    @Operation(summary = "Get offer by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Offer retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    public ResponseEntity<OfferResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(offerService.getById(id));
    }

   
    @GetMapping
    @Operation(summary = "Get all offers")
    public ResponseEntity<List<OfferResponseDto>> getAll() {

        return ResponseEntity.ok(offerService.getAll());
    }

    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an offer")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Offer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Offer not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        offerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get offers by status")
    public ResponseEntity<List<OfferResponseDto>> getByStatus(
            @PathVariable OfferStatus status) {

        return ResponseEntity.ok(
                offerService.getByStatus(status));
    }

    
    @GetMapping("/job-title/{jobTitle}")
    @Operation(summary = "Get offers by job title")
    public ResponseEntity<List<OfferResponseDto>> getByJobTitle(
            @PathVariable String jobTitle) {

        return ResponseEntity.ok(
                offerService.getByJobTitle(jobTitle));
    }

   
    @GetMapping("/application/{applicationId}")
    @Operation(summary = "Get offers by application ID")
    public ResponseEntity<List<OfferResponseDto>> getByApplicationId(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                offerService.getByApplicationId(applicationId));
    }

    
    @GetMapping("/salary")
    @Operation(summary = "Get offers by minimum salary")
    public ResponseEntity<List<OfferResponseDto>> getByMinimumSalary(
            @RequestParam Double salary) {

        return ResponseEntity.ok(
                offerService.getByMinimumSalary(salary));
    }

   
    @GetMapping("/filter")
    @Operation(summary = "Filter offers by status and job title")
    public ResponseEntity<List<OfferResponseDto>> getByStatusAndJobTitle(
            @RequestParam OfferStatus status,
            @RequestParam String jobTitle) {

        return ResponseEntity.ok(
                offerService.getByStatusAndJobTitle(status, jobTitle));
    }

   
    @GetMapping("/accepted")
    @Operation(summary = "Get accepted offers")
    public ResponseEntity<List<OfferResponseDto>> getAcceptedOffers() {

        return ResponseEntity.ok(
                offerService.getAcceptedOffers());
    }

   
    @GetMapping("/offered")
    @Operation(summary = "Get released offers")
    public ResponseEntity<List<OfferResponseDto>> getOfferedOffers() {

        return ResponseEntity.ok(
                offerService.getOfferedOffers());
    }

    
    @GetMapping("/application-native/{applicationId}")
    @Operation(summary = "Get offers by application ID using native query")
    public ResponseEntity<List<OfferResponseDto>> getOffersByApplicationNative(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                offerService.getOffersByApplicationNative(applicationId));
    }

 
    @GetMapping("/pagination")
    @Operation(summary = "Get offers with pagination")
    public ResponseEntity<Page<OfferResponseDto>> getPagination(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(
                offerService.getPagination(pageNo, pageSize));
    }

    
    @GetMapping("/sorting")
    @Operation(summary = "Get sorted offers")
    public ResponseEntity<Page<OfferResponseDto>> getSorting(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return ResponseEntity.ok(
                offerService.getSorting(pageNo, pageSize, field));
    }
}
