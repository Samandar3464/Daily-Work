package com.example.project.entity.address;

import com.example.project.entity.BaseClass;
import com.example.project.entity.Work;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Province extends BaseClass {
    @OneToMany(mappedBy = "province")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private List<CityOrDistrict> cityOrDistrictList;
    @JsonIgnore
    @OneToMany(mappedBy = "province" , cascade = jakarta.persistence.CascadeType.ALL)
    private List<Work> workList;
}

