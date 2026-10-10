
package com.placement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placement.dto.ApplicationRequestDto;
import com.placement.dto.ApplicationResponseDto;
import com.placement.enums.ApplicationStatus;
import com.placement.service.ApplicationService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    
    @Operation(
        summary = "Create or update application",
        description = "Creates a new application or updates an existing application"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Application created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid application data or deadline passed"),
        @ApiResponse(responseCode = "404", description = "Student or job drive not found"),
        @ApiResponse(responseCode = "409", description = "Student has already applied for this job drive")
    })
    @PostMapping
    public ResponseEntity<ApplicationResponseDto> createOrUpdate(
            @Valid @RequestBody ApplicationRequestDto requestDto) {

        return ResponseEntity.ok(
                applicationService.createOrUpdate(requestDto));
    }

    
    @Operation(summary = "Get application by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Application found"),
        @ApiResponse(responseCode = "404", description = "Application not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(applicationService.getById(id));
    }

   
    @Operation(summary = "Get all applications")
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDto>> getAll() {

        return ResponseEntity.ok(applicationService.getAll());
    }

    
    @Operation(summary = "Delete application")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        applicationService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

   
    @Operation(
        summary = "Get applications by status",
        description = "Finds applications with a specified ApplicationStatus"
    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApplicationResponseDto>> getByStatus(
            @PathVariable ApplicationStatus status) {

        return ResponseEntity.ok(
                applicationService.getByStatus(status));
    }

    
    @Operation(summary = "Get applications by shortlisted value")
    @GetMapping("/shortlisted")
    public ResponseEntity<List<ApplicationResponseDto>> getByShortlisted(
            @RequestParam Boolean value) {

        return ResponseEntity.ok(
                applicationService.getByShortlisted(value));
    }

    
    @Operation(summary = "Get applications by student ID")
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationResponseDto>> getByStudentId(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                applicationService.getByStudentId(studentId));
    }

 
    @Operation(summary = "Get applications by job drive ID")
    @GetMapping("/job-drive/{jobDriveId}")
    public ResponseEntity<List<ApplicationResponseDto>> getByJobDriveId(
            @PathVariable Long jobDriveId) {

        return ResponseEntity.ok(
                applicationService.getByJobDriveId(jobDriveId));
    }

    
    @Operation(summary = "Filter applications")
    @GetMapping("/filter")
    public ResponseEntity<List<ApplicationResponseDto>> getByStatusAndShortlisted(
            @RequestParam ApplicationStatus status,
            @RequestParam Boolean shortlisted) {

        return ResponseEntity.ok(
                applicationService.getByStatusAndShortlisted(
                        status, shortlisted));
    }

    // 10. Get all shortlisted applications
    @Operation(summary = "Get all shortlisted applications")
    @GetMapping("/shortlisted/all")
    public ResponseEntity<List<ApplicationResponseDto>> getShortlistedApplications() {

        return ResponseEntity.ok(
                applicationService.getShortlistedApplications());
    }

    
    @Operation(summary = "Get applied applications")
    @GetMapping("/applied")
    public ResponseEntity<List<ApplicationResponseDto>> getAppliedApplications() {

        return ResponseEntity.ok(
                applicationService.getAppliedApplications());
    }

    @Operation(summary = "Get applications with pagination")
    @GetMapping("/pagination")
    public ResponseEntity<Page<ApplicationResponseDto>> getApplications(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(
                applicationService.getApplications(pageNo, pageSize));
    }

   
    @Operation(summary = "Sort applications")
    @GetMapping("/sorting")
    public ResponseEntity<Page<ApplicationResponseDto>> getApplicationsSorted(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return ResponseEntity.ok(
                applicationService.getApplicationsSorted(
                        pageNo, pageSize, field));
    }

   
    @Operation(summary = "Shortlist application")
    @PutMapping("/{id}/shortlist")
    public ResponseEntity<String> shortlistApplication(
            @PathVariable Long id) {

        applicationService.shortlistApplication(id);

        return ResponseEntity.ok(
                "Application shortlisted successfully");
    }
}