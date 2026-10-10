
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

import com.placement.dto.StudentRequestDto;
import com.placement.dto.StudentResponseDto;
import com.placement.service.StudentService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Validated
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    
    @PostMapping
    @Operation(
        summary = "Create or update a student",
        description = "Creates a new student or updates an existing student."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student created or updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid student details")
    })
    public ResponseEntity<StudentResponseDto> createOrUpdate(
            @Valid @RequestBody StudentRequestDto requestDto) {

        StudentResponseDto response =
                studentService.createOrUpdate(requestDto);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get student by ID",
        description = "Retrieves a student using the student's ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResponseDto> getById(
            @PathVariable Long id) {

        StudentResponseDto response =
                studentService.getById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
        summary = "Get all students",
        description = "Retrieves all students."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Students retrieved successfully"
    )
    public ResponseEntity<List<StudentResponseDto>> getAll() {

        List<StudentResponseDto> response =
                studentService.getAll();

        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a student",
        description = "Deletes a student using the student's ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {

        studentService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/email/{email}")
    @Operation(
        summary = "Find student by email",
        description = "Retrieves a student using the student's email address."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Student retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentResponseDto> getByEmail(
            @PathVariable String email) {

        StudentResponseDto response =
                studentService.getByEmail(email);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/department/{department}")
    @Operation(
        summary = "Find students by department",
        description = "Retrieves students belonging to the specified department."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Students retrieved successfully"
    )
    public ResponseEntity<List<StudentResponseDto>> getByDepartment(
            @PathVariable String department) {

        List<StudentResponseDto> response =
                studentService.getByDepartment(department);

        return ResponseEntity.ok(response);
    }

  
    @GetMapping("/cgpa")
    @Operation(
        summary = "Find students by CGPA",
        description = "Retrieves students matching the supplied CGPA criterion."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Students retrieved successfully"
    )
    public ResponseEntity<List<StudentResponseDto>> getByCgpa(
            @RequestParam Double cgpa) {

        List<StudentResponseDto> response =
                studentService.getByCgpa(cgpa);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/backlogs")
    @Operation(
        summary = "Find students with backlogs",
        description = "Retrieves students who have backlogs."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Students retrieved successfully"
    )
    public ResponseEntity<List<StudentResponseDto>> getStudentsWithBacklogs() {

        List<StudentResponseDto> response =
                studentService.getStudentsWithBacklogs();

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/search")
    @Operation(
        summary = "Search students by department and CGPA",
        description = "Retrieves students matching both the specified department and CGPA criterion."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Matching students retrieved successfully"
    )
    public ResponseEntity<List<StudentResponseDto>>
            getByDepartmentAndCgpa(
                    @RequestParam String department,
                    @RequestParam Double cgpa) {

        List<StudentResponseDto> response =
                studentService.getByDepartmentAndCgpa(
                        department, cgpa);

        return ResponseEntity.ok(response);
    }

    
    @GetMapping("/pagination")
    @Operation(
        summary = "Get students with pagination",
        description = "Retrieves students for the requested page and page size."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Students retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<Page<StudentResponseDto>> getStudents(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return ResponseEntity.ok(
                studentService.getStudents(pageNo, pageSize));
    }

   
    @GetMapping("/sorting")
    @Operation(
        summary = "Get sorted students",
        description = "Retrieves a page of students sorted by the specified field."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sorted students retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination or sorting parameters")
    })
    public ResponseEntity<Page<StudentResponseDto>> getStudentsSorted(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return ResponseEntity.ok(
                studentService.getStudentsSorted(
                        pageNo, pageSize, field));
    }
}

