package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class WorkCategory extends BaseClass {

    @OneToMany(mappedBy = "workCategory")
    private List<Work> work;
}
