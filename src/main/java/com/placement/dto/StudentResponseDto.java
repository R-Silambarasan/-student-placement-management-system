package com.placement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

	private Long id;

	private String name;

	private String email;

	private String department;

	private Double cgpa;

	private Integer graduationYear;

	private Integer backlogs;

	private String skills;
}