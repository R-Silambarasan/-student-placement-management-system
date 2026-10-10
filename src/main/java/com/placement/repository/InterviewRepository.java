
package com.placement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.Interview;
import com.placement.enums.InterviewResult;
import com.placement.enums.InterviewRound;

public interface InterviewRepository
        extends JpaRepository<Interview, Long> {

    // 1. Find interviews by result
    List<Interview> findByResult(InterviewResult result);

    // 2. Find interviews by round
    List<Interview> findByRound(InterviewRound round);

    // 3. Find interviews by application
    List<Interview> findByApplicationId(Long applicationId);

    // 4. Find interviews by interviewer
    List<Interview> findByInterviewer(String interviewer);

    // 5. Find interviews by round and result
    List<Interview> findByRoundAndResult(
            InterviewRound round,
            InterviewResult result);

    // JPQL
    // 6. Find interviews for a particular application
    @Query("SELECT i FROM Interview i WHERE i.application.id = :applicationId")
    List<Interview> findInterviewsByApplication(
            @Param("applicationId") Long applicationId);

    // JPQL
    // 7. Find selected interviews
    @Query("SELECT i FROM Interview i WHERE i.result = :result")
    List<Interview> findSelectedInterviews(
            @Param("result") InterviewResult result);

    // Native SQL
    // 8. Find pending interviews
    @Query(
        value = "SELECT * FROM interview WHERE result = 'PENDING'",
        nativeQuery = true
    )
    List<Interview> findPendingInterviews();

    // Native SQL
    // 9. Find interviews by application
    @Query(
        value = "SELECT * FROM interview WHERE application_id = :applicationId",
        nativeQuery = true
    )
    List<Interview> findInterviewsByApplicationNative(
            @Param("applicationId") Long applicationId);
    
    
    List<Interview> findByInterviewDate(LocalDate interviewDate);
}

