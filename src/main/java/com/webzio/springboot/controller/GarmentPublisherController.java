package com.webzio.springboot.controller;

import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.service.GarmentPublisherService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publish")
public class GarmentPublisherController {

    private final GarmentPublisherService garmentPublisherService;

    public GarmentPublisherController(GarmentPublisherService garmentPublisherService) {
        this.garmentPublisherService = garmentPublisherService;
    }

    @PostMapping()
    @Operation(tags = "Add garment", description = "Creates a garment from a specific user")
    public ResponseEntity<String> publishGarment(@Valid @RequestBody GarmentDto dto) {
        garmentPublisherService.createGarment(dto);
        return ResponseEntity.ok("Garment created!");
    }

    @PutMapping("/{id}")
    @Operation(tags = "Add garment", description = "Updates a garment for that specific user")
    public ResponseEntity<GarmentDto> updateGarment(@PathVariable Long id, @Valid @RequestBody GarmentDto dto) {
        return ResponseEntity.ok(garmentPublisherService.updateGarment(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(tags = "Delete Garment", description = "Delete an existing garment")
    public ResponseEntity<Void> deleteGarment(@PathVariable Long id) {
        garmentPublisherService.deleteGarment(id);
        return ResponseEntity.noContent().build();
    }

}
