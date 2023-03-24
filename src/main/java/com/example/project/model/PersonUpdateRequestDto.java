package com.example.project.model;

import com.example.project.entity.ENUM.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateRequestDto {
    @NotNull
    private String name;
    private String surname;
    @NotNull
    private short age;
    @NotNull
    private Gender gender;
    private String aboutMe;
    @NotNull
    private Integer provinceId;
    @NotNull
    private Integer cityOrDistrictId;
    private String village;
    private String homeAddress;

}
