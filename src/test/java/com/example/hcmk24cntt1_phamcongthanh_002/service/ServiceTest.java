package com.example.hcmk24cntt1_phamcongthanh_002.service;

import com.example.hcmk24cntt1_phamcongthanh_002.exception.GearNotFoundException;
import com.example.hcmk24cntt1_phamcongthanh_002.model.Gear;
import com.example.hcmk24cntt1_phamcongthanh_002.repository.GearRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private GearRepository gearRepository;

    @InjectMocks
    private GearService gearService;

    @Test
    void getAllGear_ReturnList() {
        when(gearRepository.findAll()).thenReturn(Arrays.asList(new Gear(), new Gear()));
        List<Gear> result = gearService.getAllGear();
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void getById_Found() {
        Gear emp = new Gear();
        when(gearRepository.findById(1L)).thenReturn(Optional.of(emp));
        Gear result = gearService.getGearbyId(1L);
        assertNotNull(result);
        assertEquals("Ban Phim", result.getProductName());
    }

    @Test
    void getById_NotFound_ThrowException() {
        when(gearRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(GearNotFoundException.class, () -> gearService.getGearbyId(99L));
    }

    @Test
    void addGear_Success() {
        Gear emp = new Gear(null,"Chuot Gaming","C001",1500000.0,"MOUSE_HEADSET",false);
        Gear savedEmp = new Gear(1L,"Chuot Gaming","C001",1500000.0,"MOUSE_HEADSET",false);

        when(gearRepository.save(any(Gear.class))).thenReturn(savedEmp);

        Gear result = gearService.createGear(emp);
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
    }

    @Test
    void deleteGear_RemovesCorrectElement() {
        Gear emp = new Gear(1L,"Chuot Gaming","C001",1500000.0,"MOUSE_HEADSET",false);
        when(gearRepository.findById(1L)).thenReturn(Optional.of(emp));
        doNothing().when(gearRepository).delete(emp);

        gearService.deleteGear(1L);
        verify(gearRepository, times(1)).delete(emp);
    }
}