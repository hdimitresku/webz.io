package com.webzio.springboot.controller;

import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import com.webzio.springboot.service.GarmentViewerService;
import com.webzio.springboot.service.impl.GarmentViewerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/view")
public class GarmentViewerController {

    private final GarmentViewerService viewerService;

    public GarmentViewerController(GarmentViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @GetMapping("/{id}")
    @Operation(tags = "View garment", description = "Retrieves a garment by its ID")
    public ResponseEntity<GarmentDto> getGarmentById(@PathVariable Long id) {
        GarmentDto dto = viewerService.getGarmentById(id);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/user/{id}")
    @Operation(tags = "View garments posted by one publisher", description = "Retrieves all garments by their publisher ID and optional filters")
    public ResponseEntity<List<GarmentDto>> getAllGarmentsWithQuery(
            @PathVariable Long id,
            @Valid @RequestParam(required = false) GarmentSize size,
            @Valid @RequestParam(required = false) List<GarmentType> type,
            @Valid @RequestParam(required = false) Double minPrice,
            @Valid @RequestParam(required = false) Double maxPrice
    ) {
        List<GarmentDto> dtos = viewerService.getGarmentsWithQuery(id, size, type, minPrice, maxPrice);
        return ResponseEntity.ok(dtos);
    }
}
