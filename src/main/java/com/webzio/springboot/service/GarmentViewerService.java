package com.webzio.springboot.service;

import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;

import java.util.List;

public interface GarmentViewerService {
    GarmentDto getGarmentById(Long id);

    List<GarmentDto> getGarmentsByPublisherId(Long id);

    List<GarmentDto> getGarmentsWithQuery(Long publisherId, GarmentSize size, List<GarmentType> types, Double minPrice, Double maxPrice);
}
