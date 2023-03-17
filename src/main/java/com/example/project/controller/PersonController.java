package com.example.project.controller;

import com.example.project.api.ApiResponse;
import com.example.project.model.PersonRegisterDto;
import com.example.project.repository.PersonRepository;
import com.example.project.service.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//fildlarga private final qoyib beradi
//@FieldDefaults(makeFinal = true ,level = AccessLevel.PRIVATE)
@RequestMapping("api/v1/person")
@CrossOrigin("http://localhost:3000")
public class PersonController {
    private final PersonService personService;
    @PostMapping("/addPerson")
    public ApiResponse<?> addPerson(@Validated @RequestBody PersonRegisterDto personRegisterDto){
        return personService.addPerson(personRegisterDto);
    }

//    @PutMapping("/updatePerson")
//    public ApiResponse<?> updatePerson(){
//        return personService.updatePerson();
//    }
}
