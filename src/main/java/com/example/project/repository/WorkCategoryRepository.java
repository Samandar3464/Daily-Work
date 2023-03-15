package com.example.project.repository;

import com.example.project.entity.WorkCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkCategoryRepository extends JpaRepository<WorkCategory, Integer> {
    Optional<WorkCategory> findByName(String string);
}
