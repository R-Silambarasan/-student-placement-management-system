package com.placement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.placement.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
//JPQL
	@Query("select s from Student s where s.email = :email")
	Optional<Student>findByEmail(@Param("email")String email);

	 @Query("select s from Student s where s.department = :department")
   List<Student> findByDepartment(@Param("department")String department);
       
	 @Query("SELECT s FROM Student s WHERE s.cgpa >= :cgpa")
	 List<Student> findByCgpaGreaterThanEqual(@Param("cgpa") Double cgpa);
 
	 @Query(
			    value = "SELECT * FROM student WHERE backlogs > 0",
			    nativeQuery = true
			)
			List<Student> findStudentsWithBacklogs();
 
	 @Query(value = "SELECT * FROM student WHERE department = :department AND cgpa >= :cgpa", nativeQuery = true)
	 List<Student> findByDepartmentAndCgpaGreaterThanEqual(
	         @Param("department") String department,
	         @Param("cgpa") Double cgpa);
     



@Query("""
	    SELECT s FROM Student s
	    WHERE s.cgpa >= :cgpa
	    AND s.backlogs <= :backlogs
	    AND s.department = :department
	""")
	List<Student> findEligibleStudents(
	        @Param("cgpa") Double cgpa,
	        @Param("backlogs") Integer backlogs,
	        @Param("department") String department);
}
		