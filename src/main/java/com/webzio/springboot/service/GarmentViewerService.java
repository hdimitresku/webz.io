package com.webzio.springboot.service;

import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;

import java.util.List;

/**
 * Service interface for viewing garments in the marketplace.
 * This service allows users to view garments based on various filters like size, type, and price range.
 */
public interface GarmentViewerService {

    /**
     * Fetches a garment by its ID.
     *
     * @param id The ID of the garment to be fetched.
     * @return A GarmentDto representing the garment with the given ID.
     */
    GarmentDto getGarmentById(Long id);

    /**
     * Retrieves a list of garments belonging to a specific publisher.
     *
     * @param id The publisher's ID whose garments need to be fetched.
     * @return A list of GarmentDto objects representing the publisher's garments.
     */
    List<GarmentDto> getGarmentsByPublisherId(Long id);

    /**
     * Retrieves garments that match the given criteria.
     *
     * @param publisherId The publisher's ID whose garments need to be fetched.
     * @param size The desired size of the garments.
     * @param types The list of garment types to filter by.
     * @param minPrice The minimum price of garments to fetch.
     * @param maxPrice The maximum price of garments to fetch.
     * @return A list of GarmentDto objects matching the given filter criteria.
     */
    List<GarmentDto> getGarmentsWithQuery(Long publisherId, GarmentSize size, List<GarmentType> types, Double minPrice, Double maxPrice);
}
