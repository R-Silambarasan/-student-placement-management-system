
package com.placement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.Offer;
import com.placement.enums.OfferStatus;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    // 1. Find offers by status
    List<Offer> findByStatus(OfferStatus status);

    // 2. Find offers by job title
    List<Offer> findByJobTitle(String jobTitle);

    // 3. Find offers by application ID
    List<Offer> findByApplicationId(Long applicationId);

    // 4. Find offers with salary greater than or equal to given salary
    List<Offer> findBySalaryGreaterThanEqual(Double salary);

    // 5. Find offers by status and job title
    List<Offer> findByStatusAndJobTitle(
            OfferStatus status,
            String jobTitle);

    // 6. JPQL - find accepted offers
    @Query("SELECT o FROM Offer o WHERE o.status = :status")
    List<Offer> findOffersByStatus(
            @Param("status") OfferStatus status);

    // 7. JPQL - find offers by minimum salary
    @Query("SELECT o FROM Offer o WHERE o.salary >= :salary")
    List<Offer> findOffersByMinimumSalary(
            @Param("salary") Double salary);

    // 8. Native SQL - find released offers
    @Query(
        value = "SELECT * FROM offer WHERE status = 'RELEASED'",
        nativeQuery = true
    )
    List<Offer> findReleasedOffers();

    // 9. Native SQL - find offers by application ID
    @Query(
        value = "SELECT * FROM offer WHERE application_id = :applicationId",
        nativeQuery = true
    )
    List<Offer> findOffersByApplicationNative(
            @Param("applicationId") Long applicationId);
    
    List<Offer> findByStatusAndResponseDeadlineBefore(
            OfferStatus status,
            LocalDate deadline
    );
}

