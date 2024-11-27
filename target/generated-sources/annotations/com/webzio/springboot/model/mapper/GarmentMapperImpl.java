package com.webzio.springboot.model.mapper;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-27T18:50:14+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class GarmentMapperImpl implements GarmentMapper {

    @Override
    public Garment convertToGarmentEntity(GarmentDto dto) {
        if ( dto == null ) {
            return null;
        }

        Garment garment = new Garment();

        garment.setGarmentType( dto.getGarmentType() );
        garment.setDescription( dto.getDescription() );
        garment.setGarmentSize( dto.getGarmentSize() );
        garment.setPrice( dto.getPrice() );

        return garment;
    }

    @Override
    public List<Garment> convertToGarmentEntityList(List<GarmentDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Garment> list = new ArrayList<Garment>( dtos.size() );
        for ( GarmentDto garmentDto : dtos ) {
            list.add( convertToGarmentEntity( garmentDto ) );
        }

        return list;
    }

    @Override
    public GarmentDto convertToGarmentDto(Garment entity) {
        if ( entity == null ) {
            return null;
        }

        GarmentDto garmentDto = new GarmentDto();

        garmentDto.setGarmentType( entity.getGarmentType() );
        garmentDto.setGarmentSize( entity.getGarmentSize() );
        garmentDto.setDescription( entity.getDescription() );
        garmentDto.setPrice( entity.getPrice() );

        return garmentDto;
    }

    @Override
    public List<GarmentDto> convertToGarmentDtoList(List<Garment> entities) {
        if ( entities == null ) {
            return null;
        }

        List<GarmentDto> list = new ArrayList<GarmentDto>( entities.size() );
        for ( Garment garment : entities ) {
            list.add( convertToGarmentDto( garment ) );
        }

        return list;
    }

    @Override
    public void updateGarmentEntityFromDto(GarmentDto dto, Garment garment) {
        if ( dto == null ) {
            return;
        }

        garment.setGarmentType( dto.getGarmentType() );
        garment.setDescription( dto.getDescription() );
        garment.setGarmentSize( dto.getGarmentSize() );
        garment.setPrice( dto.getPrice() );
    }
}
