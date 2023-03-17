package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.PersonLoginRequestDto;
import com.example.project.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {
    private final PersonService personService;
    @GetMapping("/login")
    public ApiResponse<?> login(@RequestBody PersonLoginRequestDto personLoginRequestDto){
        return personService.login(personLoginRequestDto);
    }

}
