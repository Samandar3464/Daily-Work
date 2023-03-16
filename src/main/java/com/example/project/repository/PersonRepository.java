package com.example.project.repository;

import com.example.project.entity.Person;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person ,Integer> {

//    Optional<Person> findByUsername(String username);

    Optional<Person> findByPhoneNumber(@NotBlank @Size(min = 9) String phoneNumber);

}
