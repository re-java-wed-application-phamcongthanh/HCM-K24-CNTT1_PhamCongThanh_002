package com.example.hcmk24cntt1_phamcongthanh_002.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gears")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "gears")
public class Gear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String productName;

    @Column(nullable = false)
    @NotBlank(message = "serialCode khong duoc de trong")
    private String serialCode;

    @NotNull(message = "Gia tien khong duoc de trong")
    @Positive(message = "Gia phai lon hơn 0")
    private Double price;

    //(ENUM: KEYBOARD,MOUSE_HEADSET)
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Kieu san pham khong duoc de trong")
    private GearType type;

    @Column(nullable = false)
    private boolean isDeleted;
}
