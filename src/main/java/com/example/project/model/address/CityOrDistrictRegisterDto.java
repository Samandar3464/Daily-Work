package com.example.project.model.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityOrDistrictRegisterDto {
    @NotNull
    private String name;
    @NotNull
    private int provinceId;
}
