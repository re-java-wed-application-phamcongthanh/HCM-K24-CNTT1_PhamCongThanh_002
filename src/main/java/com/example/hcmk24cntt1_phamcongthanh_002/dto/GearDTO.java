package com.example.hcmk24cntt1_phamcongthanh_002.dto;

import com.example.hcmk24cntt1_phamcongthanh_002.model.GearType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GearDTO {
    private Long id;

    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String productName;

    @NotBlank(message = "serialCode khong duoc de trong")
    private String serialCode;

    @NotNull(message = "Gia tien khong duoc de trong")
    @Positive(message = "Gia phai lon hơn 0")
    private Double price;

    @NotNull(message = "Kieu san pham khong duoc de trong")
    private GearType type;

    private boolean isDeleted;
}
