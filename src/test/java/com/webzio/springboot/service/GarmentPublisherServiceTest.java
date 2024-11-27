package com.webzio.springboot.service;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.User;
import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import com.webzio.springboot.repository.GarmentRepository;
import com.webzio.springboot.security.service.UserServiceImpl;
import com.webzio.springboot.service.impl.GarmentPublisherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarmentPublisherServiceTest {

    @Mock
    private GarmentRepository garmentRepository;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private GarmentPublisherServiceImpl garmentPublisherService;

    @Test
    void shouldCreateGarmentSuccessfully() {
        GarmentDto garmentDto = new GarmentDto();
        garmentDto.setGarmentSize(GarmentSize.M);
        garmentDto.setGarmentType(GarmentType.T_SHIRT);
        garmentDto.setDescription("Stylish T-Shirt");
        garmentDto.setPrice(25.99);

        User mockUser = new User();
        mockUser.setUsername("test_user");

        when(userService.getAuthenticatedUsername()).thenReturn("test_user");
        when(userService.findByUsername("test_user")).thenReturn(mockUser);

        Garment garment = new Garment();
        garment.setId(1L);

        when(garmentRepository.save(any(Garment.class))).thenReturn(garment);

        Garment createdGarment = garmentPublisherService.createGarment(garmentDto);
        assertNotNull(createdGarment);
        assertEquals(1L, createdGarment.getId());
    }

    @Test
    void shouldThrowExceptionWhenGarmentNotFoundOnUpdate() {
        GarmentDto garmentDto = new GarmentDto();
        garmentDto.setDescription("Updated description");

        when(garmentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> garmentPublisherService.updateGarment(1L, garmentDto));
    }

    @Test
    void shouldDeleteGarmentSuccessfully() {
        Garment garment = new Garment();
        User mockUser = new User();
        mockUser.setUsername("test_user");
        mockUser.setId(1L);

        when(userService.getAuthenticatedUsername()).thenReturn("test_user");
        when(userService.findByUsername("test_user")).thenReturn(mockUser);

        garment.setId(1L);
        garment.setPublisher(mockUser);
        when(garmentRepository.findById(1L)).thenReturn(Optional.of(garment));
        doNothing().when(garmentRepository).deleteById(1L);

        garmentPublisherService.deleteGarment(1L);
        verify(garmentRepository, times(1)).deleteById(1L);
    }
}
