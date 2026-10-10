package com.placement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.JobDriveRequestDto;
import com.placement.dto.JobDriveResponseDto;
import com.placement.entity.JobDrive;

@Mapper(componentModel = "spring")
public interface JobDriveMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    JobDrive toEntity(JobDriveRequestDto dto);

    @Mapping(source = "company.id", target = "companyId")
    JobDriveResponseDto toResponseDto(JobDrive jobDrive);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    void updateEntity(
            JobDriveRequestDto dto,
            @MappingTarget JobDrive jobDrive);
}