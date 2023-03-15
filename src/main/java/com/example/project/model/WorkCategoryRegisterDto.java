package com.example.project.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkCategoryRegisterDto {
    @NotBlank
    @Column(unique = true)
    private String name;
}
