package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.RoleChange;
import com.example.project.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/person")
public class AdminControllerUpToPerson {
    private final PersonService personService;

    @GetMapping("/personList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getUsersList() {
        return  personService.getPersonList();
    }

    @GetMapping("/getPersoById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getPersonById(@PathVariable Integer id) {
        return personService.getById(id);
    }

    @DeleteMapping("/deletePerson/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<?> deleteUser(@PathVariable Integer id) {
        return personService.deletePerson(id);
    }

    @PutMapping("/changePersonRole")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<?> changePersonRole(@RequestBody RoleChange roleChange) {
        return personService.changePersonRole(roleChange);
    }
}
