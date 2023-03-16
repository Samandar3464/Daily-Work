package com.example.project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class   WorkRegisterDto {
    @NotNull
    private String workTitle;
    @NotNull
    private String workDescription;
    @NotNull
    private Double startPrice;
    private Double endPrice;

    @NotNull
    private Integer workCategoryId;

    @NotNull
    private Integer provinceId;

    @NotNull
    private Integer cityOrDistrictId;

    private String  village;


}
