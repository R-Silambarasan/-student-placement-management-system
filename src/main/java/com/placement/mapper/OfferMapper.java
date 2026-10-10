package com.placement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.placement.dto.OfferRequestDto;
import com.placement.dto.OfferResponseDto;
import com.placement.entity.Offer;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", ignore = true)
    Offer toEntity(OfferRequestDto dto);

    @Mapping(source = "application.id", target = "applicationId")
    OfferResponseDto toResponseDto(Offer offer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "application", ignore = true)
    void updateEntity(
            OfferRequestDto dto,
            @MappingTarget Offer offer);
}