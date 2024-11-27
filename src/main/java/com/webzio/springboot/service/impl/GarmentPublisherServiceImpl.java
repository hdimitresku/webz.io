package com.webzio.springboot.service.impl;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.User;
import com.webzio.springboot.model.dto.GarmentDto;
import com.webzio.springboot.model.mapper.GarmentMapper;
import com.webzio.springboot.repository.GarmentRepository;
import com.webzio.springboot.security.service.UserService;
import com.webzio.springboot.service.GarmentPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class GarmentPublisherServiceImpl implements GarmentPublisherService {

    private final GarmentRepository garmentRepository;
    private final UserService userService;

    @Qualifier("garmentMapperImpl")
    @Autowired
    private final GarmentMapper mapper = GarmentMapper.INSTANCE;

    public GarmentPublisherServiceImpl(GarmentRepository garmentRepository, UserService userService) {
        this.garmentRepository = garmentRepository;
        this.userService = userService;
    }

    @Override
    public Garment createGarment(GarmentDto dto){
        User user = userService.findByUsername(userService.getAuthenticatedUsername());
        Garment garment = mapper.convertToGarmentEntity(dto);
        garment.setPublisher(user);
        try {
            return garmentRepository.save(garment);

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public GarmentDto updateGarment(Long garmentId, GarmentDto dto) {
        User user = userService.findByUsername(userService.getAuthenticatedUsername());

        Garment existingGarment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garment not found"));

        if (!existingGarment.getPublisher().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this garment");
        }

        mapper.updateGarmentEntityFromDto(dto, existingGarment);

        try {
             existingGarment = garmentRepository.save(existingGarment);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to update garment");
        }
        return mapper.convertToGarmentDto(existingGarment);
    }

    @Override
    public void deleteGarment(Long garmentId) {
        User user = userService.findByUsername(userService.getAuthenticatedUsername());

        Garment garment = garmentRepository.findById(garmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garment not found"));

        if (!garment.getPublisher().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this garment");
        }

        try {
            garmentRepository.deleteById(garmentId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete garment");
        }
    }
}
