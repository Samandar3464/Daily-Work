package com.example.project.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonRegisterDto {
    @NotBlank
    @Size(min = 9)
    private String phoneNumber;
    @Pattern(regexp = "^[A-Za-z]*$")
    private String name;
    @NotBlank
    @Size(min = 6)
    private String password;

    public PersonRegisterDto(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
