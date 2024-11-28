package com.webzio.springboot.service;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;

import java.util.List;

public interface GarmentPublisherService {

    /**
     * Creates a new garment based on the provided data transfer object (DTO).
     *
     * @param dto A GarmentDto object containing the garment's details.
     * @return The newly created Garment entity.
     */
    Garment createGarment(GarmentDto dto);

    /**
     * Updates the details of an existing garment.
     *
     * @param garmentId The ID of the garment to be updated.
     * @param dto The updated garment details.
     * @return A GarmentDto containing the updated garment details.
     */
    GarmentDto updateGarment(Long garmentId, GarmentDto dto);

    /**
     * Deletes a garment from the marketplace by its ID.
     *
     * @param garmentId The ID of the garment to be deleted.
     */
    void deleteGarment(Long garmentId);
}
