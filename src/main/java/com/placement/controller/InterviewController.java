
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.placement.dto.InterviewRequestDto;
import com.placement.dto.InterviewResponseDto;
import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;
import com.placement.service.InterviewService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/interviews")
@Validated
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    
    @Operation(
        summary = "Create or update interview",
        description = "Creates a new interview or updates an existing interview"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Interview created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid interview data"),
        @ApiResponse(responseCode = "404", description = "Application or interview not found")
    })
    @PostMapping
    public ResponseEntity<InterviewResponseDto> createOrUpdate(
            @Valid @RequestBody InterviewRequestDto requestDto) {

        return ResponseEntity.ok(
                interviewService.createOrUpdate(requestDto));
    }

   
    @Operation(summary = "Get interview by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Interview found successfully"),
        @ApiResponse(responseCode = "404", description = "Interview not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(interviewService.getById(id));
    }

   
    @Operation(summary = "Get all interviews")
    @GetMapping
    public ResponseEntity<List<InterviewResponseDto>> getAll() {

        return ResponseEntity.ok(interviewService.getAll());
    }

    
    @Operation(summary = "Delete interview")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        interviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

   
    @Operation(summary = "Get interviews by result")
    @GetMapping("/result/{result}")
    public ResponseEntity<List<InterviewResponseDto>> getByResult(
            @PathVariable InterviewResult result) {

        return ResponseEntity.ok(
                interviewService.getByResult(result));
    }

    
    @Operation(summary = "Get interviews by round")
    @GetMapping("/round/{round}")
    public ResponseEntity<List<InterviewResponseDto>> getByRound(
            @PathVariable InterviewRound round) {

        return ResponseEntity.ok(
                interviewService.getByRound(round));
    }

    
    @Operation(summary = "Get interviews by application ID")
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<List<InterviewResponseDto>> getByApplicationId(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                interviewService.getByApplicationId(applicationId));
    }

   
    @Operation(summary = "Get interviews by interviewer")
    @GetMapping("/interviewer/{interviewer}")
    public ResponseEntity<List<InterviewResponseDto>> getByInterviewer(
            @PathVariable String interviewer) {

        return ResponseEntity.ok(
                interviewService.getByInterviewer(interviewer));
    }

   
    @Operation(summary = "Filter interviews by round and result")
    @GetMapping("/filter")
    public ResponseEntity<List<InterviewResponseDto>> getByRoundAndResult(
            @RequestParam InterviewRound round,
            @RequestParam InterviewResult result) {

        return ResponseEntity.ok(
                interviewService.getByRoundAndResult(round, result));
    }

    @Operation(summary = "Get selected interviews")
    @GetMapping("/selected")
    public ResponseEntity<List<InterviewResponseDto>> getSelectedInterviews() {

        return ResponseEntity.ok(
                interviewService.getSelectedInterviews());
    }

    
    @Operation(summary = "Get pending interviews")
    @GetMapping("/pending")
    public ResponseEntity<List<InterviewResponseDto>> getPendingInterviews() {

        return ResponseEntity.ok(
                interviewService.getPendingInterviews());
    }

   
    @Operation(summary = "Get interviews with pagination")
    @GetMapping("/pagination")
    public ResponseEntity<Page<InterviewResponseDto>> getInterviews(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(
                interviewService.getInterviews(pageNo, pageSize));
    }

  
    @Operation(summary = "Sort interviews")
    @GetMapping("/sorting")
    public ResponseEntity<Page<InterviewResponseDto>> getInterviewsSorted(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return ResponseEntity.ok(
                interviewService.getInterviewsSorted(
                        pageNo, pageSize, field));
    }

    
    @Operation(summary = "Update interview result")
    @PutMapping("/{id}/result")
    public ResponseEntity<String> updateInterviewResult(
            @PathVariable Long id,
            @RequestParam InterviewResult result) {

        interviewService.updateInterviewResult(id, result);

        return ResponseEntity.ok(
                "Interview result updated successfully");
    }
}

