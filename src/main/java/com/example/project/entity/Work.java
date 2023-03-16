package com.example.project.entity;

import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
//import com.example.project.entity.address.Village;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String workTitle;
    @NotNull
    private String workDescription;
    @NotNull
    private Double startPrice;
    private Double endPrice;
    private LocalDate createdTime;
    @NotNull
    @ManyToOne
    private WorkCategory workCategory;
    @ManyToOne
    @NotNull
    private Province province;
    @ManyToOne
    @NotNull
    private CityOrDistrict cityOrDistrict;
    private String village;
    @ManyToOne
    @NotNull
    private Person person;
}
