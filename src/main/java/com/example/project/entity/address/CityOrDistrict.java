package com.example.project.entity.address;

import com.example.project.entity.BaseClass;
import com.example.project.entity.Work;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CityOrDistrict extends BaseClass {
//    @OneToMany(mappedBy = "cityOrDistrict")
//    @Cascade({CascadeType.ALL})
//    @JsonIgnore
//    List<Village> villageList;

    @ManyToOne
    @JsonIgnore
    private Province province;
    @JsonIgnore
    @OneToMany(mappedBy = "cityOrDistrict" ,cascade = CascadeType.ALL )
    private List<Work> works;

}
