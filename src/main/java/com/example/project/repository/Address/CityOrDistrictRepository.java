package com.example.project.repository.Address;

import com.example.project.entity.address.CityOrDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityOrDistrictRepository extends JpaRepository<CityOrDistrict, Integer> {
    Optional<CityOrDistrict> findByName(String name);
}
