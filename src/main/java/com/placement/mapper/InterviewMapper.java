package com.placement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.InterviewRequestDto;
import com.placement.dto.InterviewResponseDto;
import com.placement.entity.Interview;

@Mapper(componentModel = "spring")
public interface InterviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", ignore = true)
    Interview toEntity(InterviewRequestDto dto);

    @Mapping(source = "application.id", target = "applicationId")
    InterviewResponseDto toResponseDto(
            Interview interview);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", ignore = true)
    void updateEntity(
            InterviewRequestDto dto,
            @MappingTarget Interview interview);
}