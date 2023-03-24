package com.example.project.controller;

import com.example.project.api.ApiResponse;
import com.example.project.model.PersonLoginRequestDto;
import com.example.project.model.PersonRegisterDto;
import com.example.project.model.PersonUpdateRequestDto;
import com.example.project.model.Verification;
import com.example.project.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @PostMapping("/addPerson")
    public ApiResponse<?> addPerson(@Validated @RequestBody PersonRegisterDto personRegisterDto) {
        return personService.addPerson(personRegisterDto);
    }

    @GetMapping("/login")
    public ApiResponse<?> login(@RequestBody PersonLoginRequestDto personLoginRequestDto) {
        return personService.login(personLoginRequestDto);
    }

    @PostMapping("/enablePerson")
    public ApiResponse<?> enablePerson(@RequestBody Verification verification) {
        return personService.verifyCodeForEnablePerson(verification);
    }

    @GetMapping("/forgetPassword")
    public ApiResponse<?> forgetPassword(@RequestBody Verification verification) {
        return personService.generateCodeForForgetPassword(verification);
    }

    @GetMapping("/verifyCode")
    public ApiResponse<?> verifyCodeForRestorePassword(@RequestBody Verification verification) {
        return personService.verifyCodeCheckerForRestorePassword(verification);
    }

    @GetMapping("/change/password")
    public ApiResponse<?> changePassword(@Validated @RequestBody PersonRegisterDto personRegisterDto) {
        return personService.changePassword(personRegisterDto);
    }

    @PutMapping("/updatePerson")
    public ApiResponse<?> updatePerson(@RequestBody PersonUpdateRequestDto personUpdateRequestDto) {
        return personService.updatePerson(personUpdateRequestDto);
    }
}
