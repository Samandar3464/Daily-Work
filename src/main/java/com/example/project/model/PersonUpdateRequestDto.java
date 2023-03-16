package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateRequestDto {
    private String name;
    private String surname;
    private int age;
    private String gender;
    private String aboutMe;
    private Integer provinceId;
    private Integer cityOrDistrictId;
    private String village;
    private String homeAddress;

}
