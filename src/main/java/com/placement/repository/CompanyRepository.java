package com.placement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.Company;

public interface CompanyRepository
        extends JpaRepository<Company, Long> {
	 // 1. Find by email
	// 1. JPQL - Find by email
	@Query("SELECT c FROM Company c WHERE c.email = :email")
	Optional<Company> findByEmail(@Param("email") String email);

	// 2. JPQL - Find by industry
	@Query("SELECT c FROM Company c WHERE c.industry = :industry")
	List<Company> findByIndustry(@Param("industry") String industry);

 // 3. Native SQL - Find by location
    @Query(value = "SELECT * FROM company WHERE location = :location", nativeQuery = true)
    List<Company> findByLocation(@Param("location") String location);

    // 4. Native SQL - Find by name
    @Query(value = "SELECT * FROM company WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))",
           nativeQuery = true)
    List<Company> findByNameContainingIgnoreCase(@Param("name") String name);

    // 5. Native SQL - Find by industry and location
    @Query(value = "SELECT * FROM company WHERE industry = :industry AND location = :location",
           nativeQuery = true)
    List<Company> findByIndustryAndLocation(
            @Param("industry") String industry,
            @Param("location") String location);
}