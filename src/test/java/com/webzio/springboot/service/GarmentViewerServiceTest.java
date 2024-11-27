package com.webzio.springboot.service;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import com.webzio.springboot.repository.GarmentRepository;
import com.webzio.springboot.service.impl.GarmentViewerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarmentViewerServiceTest {

    @Mock
    private GarmentRepository garmentRepository;

    @InjectMocks
    private GarmentViewerServiceImpl garmentViewerService;

    @Test
    void shouldReturnGarmentsWithQuery() {
        Garment garment = new Garment();
        garment.setGarmentSize(GarmentSize.M);
        garment.setGarmentType(GarmentType.T_SHIRT);

        when(garmentRepository.findByFilters(null, GarmentSize.M, List.of(GarmentType.T_SHIRT), 10.0, 50.0))
                .thenReturn(Optional.of(List.of(garment)));

        List<GarmentDto> garments = garmentViewerService.getGarmentsWithQuery(null, GarmentSize.M, List.of(GarmentType.T_SHIRT), 10.0, 50.0);
        assertEquals(1, garments.size());
    }

    @Test
    void shouldThrowExceptionWhenGarmentsNotFound() {
        when(garmentRepository.findByFilters(null, null, null, null, null))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> garmentViewerService.getGarmentsWithQuery(null, null, null, null, null));
    }
}
