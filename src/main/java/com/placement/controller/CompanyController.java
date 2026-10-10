package com.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placement.dto.CompanyRequestDto;
import com.placement.dto.CompanyResponseDto;
import com.placement.service.CompanyService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/companies")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;

  
    @Operation(
        summary = "Create or update company",
        description = "Creates a new company or updates an existing company"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Company created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid company data"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PostMapping
    public ResponseEntity<CompanyResponseDto> createOrUpdate(
            @Valid @RequestBody CompanyRequestDto requestDto) {

        CompanyResponseDto response =
                companyService.createOrUpdate(requestDto);

        return ResponseEntity.ok(response);
    }

    
    @Operation(
        summary = "Get company by ID",
        description = "Retrieves company details using the company ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Company found successfully"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                companyService.getById(id));
    }

    
    @Operation(
        summary = "Get all companies",
        description = "Retrieves all registered companies"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Companies retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<CompanyResponseDto>> getAll() {

        List<CompanyResponseDto> response =
                companyService.getAll();

        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Delete company",
        description = "Deletes a company using its ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        companyService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    @Operation(
        summary = "Get company by email",
        description = "Finds a company using its registered email address"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Company found successfully"),
        @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<CompanyResponseDto> getByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                companyService.getByEmail(email));
    }

    
    @Operation(
        summary = "Get companies by industry",
        description = "Retrieves companies belonging to a specified industry"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Companies retrieved successfully"
    )
    @GetMapping("/industry/{industry}")
    public ResponseEntity<List<CompanyResponseDto>> getByIndustry(
            @PathVariable String industry) {

        return ResponseEntity.ok(
                companyService.getByIndustry(industry));
    }

   
    @Operation(
        summary = "Get companies by location",
        description = "Retrieves companies registered in a specified location"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Companies retrieved successfully"
    )
    @GetMapping("/location/{location}")
    public ResponseEntity<List<CompanyResponseDto>> getByLocation(
            @PathVariable String location) {

        return ResponseEntity.ok(
                companyService.getByLocation(location));
    }

   
    @Operation(
        summary = "Search companies by name",
        description = "Searches for companies matching the provided name"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Search results retrieved successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<List<CompanyResponseDto>> getByName(
            @RequestParam String name) {

        return ResponseEntity.ok(
                companyService.getByName(name));
    }

    
    @Operation(
        summary = "Filter companies",
        description = "Finds companies matching both industry and location"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Filtered companies retrieved successfully"
    )
    @GetMapping("/filter")
    public ResponseEntity<List<CompanyResponseDto>> getByIndustryAndLocation(
            @RequestParam String industry,
            @RequestParam String location) {

        return ResponseEntity.ok(
                companyService.getByIndustryAndLocation(
                        industry,
                        location));
    }

    
    @Operation(
        summary = "Get companies with pagination",
        description = "Retrieves companies for the requested page and page size"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Company page retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    @GetMapping("/pagination")
    public ResponseEntity<Page<CompanyResponseDto>> getCompanies(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(
                companyService.getCompanies(
                        pageNo,
                        pageSize));
    }

    
    @Operation(
        summary = "Sort companies",
        description = "Retrieves companies sorted by the specified field"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sorted companies retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    @GetMapping("/sorting")
    public ResponseEntity<Page<CompanyResponseDto>> getCompaniesSorted(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return ResponseEntity.ok(
                companyService.getCompaniesSorted(
                        pageNo,
                        pageSize,
                        field));
    }
}
