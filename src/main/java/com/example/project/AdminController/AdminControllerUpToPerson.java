package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.service.PersonService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/person")
public class AdminControllerUpToPerson {
    private final PersonService personService;

    @GetMapping("/personList")
    public ApiResponse<?> getUsersList() {
        return new ApiResponse<>(200, personService.getPersonList());
    }

    @DeleteMapping("/deletePerson/{id}")
    public ApiResponse<?> deleteUser(@PathVariable Integer id) {
        personService.deletePerson(id);
        return new ApiResponse<>("Deleted", 200);
    }

    @GetMapping("/getPersoById/{id}")
    public ApiResponse<?> getPersonById(@PathVariable Integer id) {
        return personService.getById(id);
    }
}
