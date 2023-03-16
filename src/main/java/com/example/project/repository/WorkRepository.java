package com.example.project.repository;

import com.example.project.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work , Long> {
Optional<Work> findAllByPersonId(Integer person_id);
void deleteByIdAndPersonId(Long id, Integer person_id);
}
