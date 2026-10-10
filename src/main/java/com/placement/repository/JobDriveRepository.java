
package com.placement.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.JobDrive;
import com.placement.enums.JobDriveStatus;

public interface JobDriveRepository
        extends JpaRepository<JobDrive, Long> {

    // 1. Find by status - JPQL
    @Query("SELECT j FROM JobDrive j WHERE j.status = :status")
    List<JobDrive> findByStatus(
            @Param("status") JobDriveStatus status);

    // 2. Find by eligible department - JPQL
    @Query("SELECT j FROM JobDrive j WHERE j.eligibleDepartment = :eligibleDepartment")
    List<JobDrive> findByEligibleDepartment(
            @Param("eligibleDepartment") String eligibleDepartment);

    // 3. Find by minimum CGPA - Native SQL
    @Query(
        value = "SELECT * FROM job_drive WHERE minimum_cgpa >= :cgpa",
        nativeQuery = true
    )
    List<JobDrive> findByMinimumCgpaGreaterThanEqual(
            @Param("cgpa") Double cgpa);

    // 4. Find by location - Native SQL
    @Query(
        value = "SELECT * FROM job_drive WHERE location = :location",
        nativeQuery = true
    )
    List<JobDrive> findByLocation(
            @Param("location") String location);

    // 5. Find by status and department - Native SQL
    @Query("""
    	    SELECT j FROM JobDrive j
    	    WHERE j.status = :status
    	    AND j.eligibleDepartment = :department
    	    """)
    	List<JobDrive> findByStatusAndEligibleDepartment(
    	        @Param("status") JobDriveStatus status,
    	        @Param("department") String department
    	);
    
    
 // Find expired job drives that are still OPEN 
    List<JobDrive> findByApplicationDeadlineBeforeAndStatus( LocalDateTime deadline, JobDriveStatus status );
    List<JobDrive> findByDriveDateBetweenAndStatus(
            LocalDate startDate,
            LocalDate endDate,
            JobDriveStatus status
    );
}

