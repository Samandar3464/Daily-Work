package com.example.project.entity;

import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
//import com.example.project.entity.address.Village;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    @NotNull
    @ManyToOne()
    @Cascade(CascadeType.ALL)
    private WorkCategory workCategory;
    @OneToOne
    @NotNull
    private Province province;
    @OneToOne
    @NotNull
    private CityOrDistrict cityOrDistrict;

    private String village;
    @ManyToOne
    @NotNull
    private Person person;
}
