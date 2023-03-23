package com.example.project.model;

import com.example.project.entity.ENUM.Gender;
import com.example.project.entity.Person;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDto {
    private Integer id;
    private String name;
    private String phoneNumber;
    private String surname;

    private short age;

    private String gender;
    private String aboutMe;

    private String provinceName;

    private String cityOrDistrictName;
    private String village;
    private String homeAddress;

    public PersonResponseDto(Integer id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public PersonResponseDto of(Person person) {
        return PersonResponseDto.builder()
                .id(person.getId())
                .name(person.getName())
                .surname(person.getSurname())
                .phoneNumber(person.getPhoneNumber())
                .age(person.getAge())
                .gender(person.getGender().toString())
                .aboutMe(person.getAboutMe())
                .provinceName(person.getProvince().getName())
                .cityOrDistrictName(person.getCityOrDistrict().getName())
                .village(person.getVillage())
                .homeAddress(person.getHomeAddress())
                .build();
    }
}
