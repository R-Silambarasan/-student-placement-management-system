
package com.placement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.ApplicationRequestDto;
import com.placement.dto.ApplicationResponseDto;
import com.placement.entity.Application;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "jobDrive", ignore = true)
    Application toEntity(ApplicationRequestDto dto);

    
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "jobDrive.id", target = "jobDriveId")
    ApplicationResponseDto toResponseDto(
            Application application);

   
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "jobDrive", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "appliedAt", ignore = true)
    void updateEntity(
            ApplicationRequestDto dto,
            @MappingTarget Application application);
}
