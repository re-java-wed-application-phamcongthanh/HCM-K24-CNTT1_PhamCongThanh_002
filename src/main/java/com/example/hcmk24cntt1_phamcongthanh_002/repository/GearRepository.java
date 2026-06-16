package com.example.hcmk24cntt1_phamcongthanh_002.repository;

import com.example.hcmk24cntt1_phamcongthanh_002.model.Gear;
import com.example.hcmk24cntt1_phamcongthanh_002.model.GearType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GearRepository extends JpaRepository<Gear, Long> {

    @Query("SELECT g FROM Gear g WHERE " +
           "(:productName IS NULL OR LOWER(g.productName) LIKE LOWER(CONCAT('%', :productName, '%'))) AND " +
           "(:type IS NULL OR g.type = :type) AND " +
           "(:minPrice IS NULL OR g.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR g.price <= :maxPrice) AND " +
           "g.isDeleted = false")
    Page<Gear> searchGears(
            @Param("productName") String productName,
            @Param("type") GearType type,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
