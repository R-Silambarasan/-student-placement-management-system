package com.placement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.CompanyRequestDto;
import com.placement.dto.CompanyResponseDto;
import com.placement.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(target = "id", ignore = true)
    Company toEntity(CompanyRequestDto dto);

    CompanyResponseDto toResponseDto(Company company);

    @Mapping(target = "id", ignore = true)
    void updateEntity(
            CompanyRequestDto dto,
            @MappingTarget Company company
    );
}