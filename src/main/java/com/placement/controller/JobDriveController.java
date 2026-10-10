
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

import com.placement.dto.JobDriveRequestDto;
import com.placement.dto.JobDriveResponseDto;
import com.placement.dto.StudentResponseDto;
import com.placement.enums.JobDriveStatus;
import com.placement.service.JobDriveService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/job-drives")
@Validated
public class JobDriveController {

    @Autowired
    private JobDriveService jobDriveService;

    
    @PostMapping
    @Operation(
        summary = "Create or update a job drive",
        description = "Creates a new job drive or updates an existing job drive."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Job drive created or updated successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid job drive details"
        )
    })
    public ResponseEntity<JobDriveResponseDto> createOrUpdate(
            @Valid @RequestBody JobDriveRequestDto requestDto) {

        JobDriveResponseDto response =
                jobDriveService.createOrUpdate(requestDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Get job drive by ID",
        description = "Retrieves a job drive using its ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Job drive retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Job drive not found"
        )
    })
    public ResponseEntity<JobDriveResponseDto> getById(
            @PathVariable Long id) {

        JobDriveResponseDto response =
                jobDriveService.getById(id);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping
    @Operation(
        summary = "Get all job drives",
        description = "Retrieves all job drives."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Job drives retrieved successfully"
    )
    public ResponseEntity<List<JobDriveResponseDto>> getAll() {

        List<JobDriveResponseDto> response =
                jobDriveService.getAll();

        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a job drive",
        description = "Deletes a job drive using its ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Job drive deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Job drive not found"
        )
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        jobDriveService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/status/{status}")
    @Operation(
        summary = "Get job drives by status",
        description = "Retrieves job drives matching the specified status."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Job drives retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid job drive status"
        )
    })
    public ResponseEntity<List<JobDriveResponseDto>> getByStatus(
            @PathVariable JobDriveStatus status) {

        return ResponseEntity.ok(
                jobDriveService.getByStatus(status));
    }

    @GetMapping("/department/{department}")
    @Operation(
        summary = "Get job drives by department",
        description = "Retrieves job drives for the specified eligible department."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Job drives retrieved successfully"
    )
    public ResponseEntity<List<JobDriveResponseDto>>
            getByEligibleDepartment(
                    @PathVariable String department) {

        return ResponseEntity.ok(
                jobDriveService.getByEligibleDepartment(
                        department));
    }

  
    @GetMapping("/cgpa")
    @Operation(
        summary = "Get job drives by minimum CGPA",
        description = "Retrieves job drives whose minimum CGPA requirement matches the supplied value."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Job drives retrieved successfully"
    )
    public ResponseEntity<List<JobDriveResponseDto>>
            getByMinimumCgpa(
                    @RequestParam Double cgpa) {

        return ResponseEntity.ok(
                jobDriveService.getByMinimumCgpa(cgpa));
    }

    
    @GetMapping("/location/{location}")
    @Operation(
        summary = "Get job drives by location",
        description = "Retrieves job drives available at the specified location."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Job drives retrieved successfully"
    )
    public ResponseEntity<List<JobDriveResponseDto>>
            getByLocation(
                    @PathVariable String location) {

        return ResponseEntity.ok(
                jobDriveService.getByLocation(location));
    }

    
    @GetMapping("/filter")
    @Operation(
        summary = "Filter job drives",
        description = "Filters job drives by status and eligible department."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Filtered job drives retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid status or filter parameters"
        )
    })
    public ResponseEntity<List<JobDriveResponseDto>>
            getByStatusAndDepartment(
                    @RequestParam JobDriveStatus status,
                    @RequestParam String department) {

        return ResponseEntity.ok(
                jobDriveService.getByStatusAndDepartment(
                        status,
                        department));
    }

  
    @GetMapping("/pagination")
    @Operation(
        summary = "Get job drives with pagination",
        description = "Retrieves job drives for the requested page and page size."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Job drives retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination parameters"
        )
    })
    public ResponseEntity<Page<JobDriveResponseDto>>
            getJobDrives(
                    @RequestParam int pageNo,
                    @RequestParam int pageSize) {

        return ResponseEntity.ok(
                jobDriveService.getJobDrives(
                        pageNo,
                        pageSize));
    }

   
    @GetMapping("/sorting")
    @Operation(
        summary = "Get sorted job drives",
        description = "Retrieves a page of job drives sorted by the specified field."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Sorted job drives retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid pagination or sorting parameters"
        )
    })
    public ResponseEntity<Page<JobDriveResponseDto>>
            getJobDrivesSorted(
                    @RequestParam int pageNo,
                    @RequestParam int pageSize,
                    @RequestParam String field) {

        return ResponseEntity.ok(
                jobDriveService.getJobDrivesSorted(
                        pageNo,
                        pageSize,
                        field));
    }

   
    @GetMapping("/{jobDriveId}/eligible-students")
    @Operation(
        summary = "Get eligible students for a job drive",
        description = "Retrieves students who meet the eligibility requirements for the specified job drive."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Eligible students retrieved successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Job drive not found"
        )
    })
    public ResponseEntity<List<StudentResponseDto>> getEligibleStudents(
            @PathVariable Long jobDriveId) {

        return ResponseEntity.ok(
                jobDriveService.getEligibleStudents(
                        jobDriveId));
    }
}

