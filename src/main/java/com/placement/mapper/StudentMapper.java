package com.placement.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.StudentRequestDto;
import com.placement.dto.StudentResponseDto;
import com.placement.entity.Student;



@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequestDto dto);

    StudentResponseDto toResponseDto(Student student);

    @Mapping(target = "id", ignore = true)
    void updateEntity(
            StudentRequestDto dto,
            @MappingTarget Student student
    );
}




    

    
