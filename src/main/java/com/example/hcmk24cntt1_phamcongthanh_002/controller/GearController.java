package com.example.hcmk24cntt1_phamcongthanh_002.controller;

import com.example.hcmk24cntt1_phamcongthanh_002.dto.GearDTO;
import com.example.hcmk24cntt1_phamcongthanh_002.service.GearService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gears")
@RequiredArgsConstructor
@Slf4j
public class GearController {
    private final GearService gearService;

    @GetMapping
    public ResponseEntity<List<GearDTO>> getAllGear() {
        log.info("GET /api/gears called");
        return ResponseEntity.ok(gearService.getAllGear());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GearDTO> getGearById(@PathVariable Long id) {
        log.info("GET /api/gears/{} called", id);
        return ResponseEntity.ok(gearService.getGearbyId(id));
    }

    @PostMapping
    public ResponseEntity<GearDTO> createGear(@Valid @RequestBody GearDTO gearDTO) {
        log.info("POST /api/gears called");
        return ResponseEntity.status(HttpStatus.CREATED).body(gearService.createGear(gearDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GearDTO> updateGear(@PathVariable Long id, @Valid @RequestBody GearDTO gearDetailDTO) {
        log.info("PUT /api/gears/{} called", id);
        return ResponseEntity.ok(gearService.updateGear(id, gearDetailDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGear(@PathVariable Long id) {
        log.info("DELETE /api/gears/{} called", id);
        gearService.deleteGear(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<GearDTO>> searchGears(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("GET /api/gears/search called with productName={}, type={}, minPrice={}, maxPrice={}, page={}, size={}",
                productName, type, minPrice, maxPrice, page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(gearService.searchGears(productName, type, minPrice, maxPrice, pageable));
    }
}
