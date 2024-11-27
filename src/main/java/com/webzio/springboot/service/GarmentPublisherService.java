package com.webzio.springboot.service;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;

import java.util.List;

public interface GarmentPublisherService {

    Garment createGarment(GarmentDto dto);

    GarmentDto updateGarment(Long garmentId, GarmentDto dto);

    void deleteGarment(Long garmentId);
}
