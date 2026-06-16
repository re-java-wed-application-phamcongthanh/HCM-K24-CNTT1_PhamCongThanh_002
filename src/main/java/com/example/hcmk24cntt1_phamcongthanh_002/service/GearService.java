package com.example.hcmk24cntt1_phamcongthanh_002.service;

import com.example.hcmk24cntt1_phamcongthanh_002.dto.GearDTO;
import com.example.hcmk24cntt1_phamcongthanh_002.exception.GearNotFoundException;
import com.example.hcmk24cntt1_phamcongthanh_002.model.Gear;
import com.example.hcmk24cntt1_phamcongthanh_002.model.GearType;
import com.example.hcmk24cntt1_phamcongthanh_002.repository.GearRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GearService {
    private final GearRepository gearRepository;

    private GearDTO convertToDTO(Gear gear) {
        if (gear == null) return null;
        return new GearDTO(
                gear.getId(),
                gear.getProductName(),
                gear.getSerialCode(),
                gear.getPrice(),
                gear.getType(),
                gear.isDeleted()
        );
    }

    private Gear convertToEntity(GearDTO dto) {
        if (dto == null) return null;
        Gear gear = new Gear();
        gear.setId(dto.getId());
        gear.setProductName(dto.getProductName());
        gear.setSerialCode(dto.getSerialCode());
        gear.setPrice(dto.getPrice());
        gear.setType(dto.getType());
        gear.setDeleted(dto.isDeleted());
        return gear;
    }

    public List<GearDTO> getAllGear() {
        return gearRepository.findAll().stream()
                .filter(gear -> !gear.isDeleted())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GearDTO getGearbyId(Long id) {
        Gear gear = gearRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Khong tim thay thiet bi voi id: {}", id);
                    return new GearNotFoundException("Khong tim thay thiet bi voi id: " + id);
                });
        if (gear.isDeleted()) {
            throw new GearNotFoundException("Khong tim thay thiet bi voi id: " + id);
        }
        return convertToDTO(gear);
    }

    public GearDTO createGear(GearDTO newGearDTO) {
        Gear gear = convertToEntity(newGearDTO);
        gear.setDeleted(false);
        Gear savedGear = gearRepository.save(gear);
        return convertToDTO(savedGear);
    }

    public GearDTO updateGear(Long id, GearDTO gearDetail) {
        Gear existGear = gearRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Khong tim thay thiet bi voi id: {}", id);
                    return new GearNotFoundException("Khong tim thay thiet bi voi id: " + id);
                });
        if (existGear.isDeleted()) {
            throw new GearNotFoundException("Khong tim thay thiet bi voi id: " + id);
        }
        existGear.setProductName(gearDetail.getProductName());
        existGear.setSerialCode(gearDetail.getSerialCode());
        existGear.setPrice(gearDetail.getPrice());
        existGear.setType(gearDetail.getType());
        existGear.setDeleted(gearDetail.isDeleted());
        
        Gear updatedGear = gearRepository.save(existGear);
        return convertToDTO(updatedGear);
    }

    public void deleteGear(Long id) {
        Gear existGear = gearRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Khong tim thay thiet bi voi id: {}", id);
                    return new GearNotFoundException("Khong tim thay thiet bi voi id: " + id);
                });
        existGear.setDeleted(true);
        gearRepository.save(existGear);
    }

    public Page<GearDTO> searchGears(String productName, String type, Double minPrice, Double maxPrice, Pageable pageable) {
        GearType gearType = null;
        if (type != null && !type.trim().isEmpty()) {
            try {
                gearType = GearType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Kieu san pham khong hop le: {}", type);
                throw new IllegalArgumentException("Kieu san pham khong hop le: " + type);
            }
        }
        Page<Gear> gears = gearRepository.searchGears(productName, gearType, minPrice, maxPrice, pageable);
        return gears.map(this::convertToDTO);
    }
}
