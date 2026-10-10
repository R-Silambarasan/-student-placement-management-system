
package com.placement.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.placement.enums.JobDriveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double minimumCgpa;

    @Column(nullable = false)
    private Integer maximumBacklogs;

    @Column(nullable = false)
    private String eligibleDepartment;

    @Column(nullable = false)
    private LocalDate driveDate;

    @Column(nullable = false)
    private LocalDateTime applicationDeadline;

    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobDriveStatus status;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}

