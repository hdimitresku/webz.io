package com.webzio.springboot.model.dto;

import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GarmentDto {


    @NotNull(message = "{garment_type_not_empty}")
    private GarmentType garmentType;

    @NotNull(message = "{garment_size_not_empty}")
    private GarmentSize garmentSize;

    @NotEmpty(message = "{description_not_empty}" )
    private String description;

    @NotNull(message = "{price_not_empty}")
    private Double price;


    public GarmentType getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(GarmentType garmentType) {
        this.garmentType = garmentType;
    }

    public GarmentSize getGarmentSize() {
        return garmentSize;
    }

    public void setGarmentSize(GarmentSize garmentSize) {
        this.garmentSize = garmentSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
