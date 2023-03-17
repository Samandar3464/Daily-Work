package com.example.project.controller;

import com.example.project.api.ApiResponse;
import com.example.project.model.WorkRegisterDto;
import com.example.project.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/work")
public class WorkController {
    private final WorkService workService;

    @PostMapping("/addWork")
    public ApiResponse<?> addWork(@Validated @RequestBody WorkRegisterDto workRegisterDto) {
        return workService.add(workRegisterDto);
    }

    @GetMapping("/getWorkList")
    public ApiResponse<?> getPersonWorkList() {
        return workService.getPersonWorksList();
    }

    @GetMapping("/getWorkById/{id}")
    public ApiResponse<?> getWorkById(@PathVariable Long id) {
        return workService.getById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<?> deletePersonWorkById(@PathVariable Long id) {
        return workService.deletePersonWork(id);
    }

    @PutMapping("/updateCategory/{id}")
    public ApiResponse<?> updateWork(@PathVariable Long id,@Validated @RequestBody WorkRegisterDto workRegisterDto) {
        return workService.update(workRegisterDto, id);
    }

}
