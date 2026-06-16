package com.example.hcmk24cntt1_phamcongthanh_002.controller;

import com.example.hcmk24cntt1_phamcongthanh_002.exception.GearNotFoundException;
import com.example.hcmk24cntt1_phamcongthanh_002.model.Gear;
import com.example.hcmk24cntt1_phamcongthanh_002.service.GearService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GearController.class)
class GearControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GearService gearService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllGear() throws Exception {
        when(gearService.getAllGear()).thenReturn(Arrays.asList(new Gear()));

        mockMvc.perform(get("/api/gears"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van A"));
    }

    @Test
    void testGetGearById_Found() throws Exception {
        when(gearService.getGearbyId(1L)).thenReturn(new Gear());

        mockMvc.perform(get("/api/gears/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"));
    }

    @Test
    void testGetGearById_NotFound() throws Exception {
        when(gearService.getGearbyId(99L)).thenThrow(new GearNotFoundException("Gear not found with id: 99"));

        mockMvc.perform(get("/api/gears/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateGear() throws Exception {
        Gear input = new Gear(null,"Chuot Gaming","C001",1500000.0,"MOUSE_HEADSET",false);
        Gear output = new Gear(1L,"Chuot Gaming","C001",1500000.0,"MOUSE_HEADSET",false);

        when(gearService.createGear(any(Gear.class))).thenReturn(output);

        mockMvc.perform(post("/api/gears")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }
}