package com.webzio.springboot.model;

import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a Garment entity in the system.
 * This entity is used to store information about a garment being sold or published in the marketplace.
 * A garment is associated with a publisher (user) and contains details such as the garment type,
 * size, description, price, and creation date.
 *
 * The class is mapped to the "garment" table in the database.
 */
@Entity
@Table(name = "garment")
public class Garment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GarmentType garmentType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id", nullable = false)
    private User publisher;

    @Enumerated(EnumType.STRING)
    private GarmentSize garmentSize;

    @Column(nullable = false)
    private Double price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GarmentType getGarmentType() {
        return garmentType;
    }

    public void setGarmentType(GarmentType garmentType) {
        this.garmentType = garmentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public GarmentSize getGarmentSize() {
        return garmentSize;
    }

    public void setGarmentSize(GarmentSize garmentSize) {
        this.garmentSize = garmentSize;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}