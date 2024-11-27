package com.webzio.springboot.model.mapper;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GarmentMapper {

    GarmentMapper INSTANCE = Mappers.getMapper(GarmentMapper.class);

    Garment convertToGarmentEntity(GarmentDto dto);

    List<Garment> convertToGarmentEntityList(List<GarmentDto> dtos);

    GarmentDto convertToGarmentDto(Garment entity);

    List<GarmentDto> convertToGarmentDtoList(List<Garment> entities);

    void updateGarmentEntityFromDto(GarmentDto dto, @MappingTarget Garment garment);



}
