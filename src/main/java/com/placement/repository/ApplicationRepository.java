
package com.placement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.Application;
import com.placement.enums.ApplicationStatus;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    
    List<Application> findByStatus(ApplicationStatus status);

    
    List<Application> findByShortlisted(Boolean shortlisted);

    
    List<Application> findByStudentId(Long studentId);

    
    List<Application> findByJobDriveId(Long jobDriveId);

    
    List<Application> findByStatusAndShortlisted(
            ApplicationStatus status,
            Boolean shortlisted);

   
    Optional<Application> findByStudentIdAndJobDriveId(
            Long studentId,
            Long jobDriveId);

    
    
    @Query("SELECT a FROM Application a WHERE a.student.id = :studentId")
    List<Application> findApplicationsByStudent(
            @Param("studentId") Long studentId);

   
    @Query("SELECT a FROM Application a WHERE a.shortlisted = true")
    List<Application> findShortlistedApplications();

  
    @Query(
        value = "SELECT * FROM application WHERE status = 'APPLIED'",
        nativeQuery = true
    )
    List<Application> findAppliedApplications();

    
    @Query(
        value = "SELECT * FROM application WHERE job_drive_id = :jobDriveId",
        nativeQuery = true
    )
    List<Application> findApplicationsByJobDrive(
            @Param("jobDriveId") Long jobDriveId);
}