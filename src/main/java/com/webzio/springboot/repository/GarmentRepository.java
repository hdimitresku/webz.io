package com.webzio.springboot.repository;

import com.webzio.springboot.model.Garment;
import com.webzio.springboot.model.enumerators.GarmentSize;
import com.webzio.springboot.model.enumerators.GarmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GarmentRepository extends JpaRepository<Garment, Long> {

    Optional<Garment> findGarmentById(Long id);

    List<Garment> findAllByPublisher_Id(Long id);

    List<Garment> findAllByPublisher_IdAndGarmentType(Long id, GarmentType type);

    List<Garment> findAllByPublisher_IdAndGarmentSize(Long id, GarmentSize size);

    List<Garment> findByPublisher_IdAndGarmentSizeBetween(Long publisher_id, GarmentSize size, GarmentSize size2);

    @Query("SELECT g FROM Garment g WHERE g.publisher.id = :publisherId " +
            "AND (:size IS NULL OR g.garmentSize = :size) " +
            "AND (:types IS NULL OR g.garmentType IN (:types)) " +
            "AND (:minPrice IS NULL OR g.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR g.price <= :maxPrice)")
    Optional<List<Garment>> findByFilters(
            @Param("publisherId") Long publisherId,
            @Param("size") GarmentSize size,
            @Param("types") List<GarmentType> types,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );



}
