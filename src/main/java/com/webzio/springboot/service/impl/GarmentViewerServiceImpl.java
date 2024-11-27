package com.webzio.springboot.service.impl;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import com.webzio.springboot.model.mapper.GarmentMapper;
import com.webzio.springboot.repository.GarmentRepository;
import com.webzio.springboot.service.GarmentViewerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GarmentViewerServiceImpl implements GarmentViewerService {

    private final GarmentRepository garmentRepository;

    @Qualifier("garmentMapperImpl")
    @Autowired
    private final GarmentMapper mapper = GarmentMapper.INSTANCE;

    public GarmentViewerServiceImpl(GarmentRepository garmentRepository) {
        this.garmentRepository = garmentRepository;
    }

    @Override
    public GarmentDto getGarmentById(Long id){
        return mapper.convertToGarmentDto(garmentRepository.findGarmentById(id).orElseThrow());
    }

    @Override
    public List<GarmentDto> getGarmentsByPublisherId(Long id){
        return mapper.convertToGarmentDtoList(garmentRepository.findAllByPublisher_Id(id));
    }

    @Override
    public List<GarmentDto> getGarmentsWithQuery(Long publisherId, GarmentSize size, List<GarmentType> types, Double minPrice, Double maxPrice) {
        List<Garment> garments = garmentRepository.findByFilters(publisherId, size, types, minPrice, maxPrice).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.convertToGarmentDtoList(garments);
    }
}
