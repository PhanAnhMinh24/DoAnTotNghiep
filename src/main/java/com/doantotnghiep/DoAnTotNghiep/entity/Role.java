package com.doantotnghiep.DoAnTotNghiep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isDefault; // Trường này

    public boolean getIsDefault() { // Đảm bảo có getter này
        return isDefault;
    }
}
