
package com.placement.dto;

import java.time.LocalDate;

import com.placement.enums.OfferStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferResponseDto {

    private Long id;

    private Long applicationId;

    private LocalDate offerDate;

    private String jobTitle;

    private Double salary;

    private LocalDate joiningDate;

    private OfferStatus status;
    
    private LocalDate responseDeadline;
}

