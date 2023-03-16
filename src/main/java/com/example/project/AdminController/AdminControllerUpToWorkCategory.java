package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.WorkCategoryRegisterDto;
import com.example.project.service.WorkCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category/admin")
public class AdminControllerUpToWorkCategory {
    private final WorkCategoryService workCategoryService;

    @PostMapping("/addCategory")
    public ApiResponse<?> addCategory(@Validated @RequestBody WorkCategoryRegisterDto workCategoryRegisterDto) {
        return workCategoryService.add(workCategoryRegisterDto);
    }

    @GetMapping("/getCategoryList")
    public ApiResponse<?> getCategoryList() {
        return workCategoryService.getList();
    }

    @GetMapping("/getCategoryById/{id}")
    public ApiResponse<?> getCategoryById(@PathVariable Integer id) {
        return workCategoryService.getById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<?> deleteCategoryById(@PathVariable Integer id) {
        return workCategoryService.delete(id);
    }

    @PutMapping("/updateCategory/{id}")
    public ApiResponse<?> updateCategory(@PathVariable Integer id, @RequestBody WorkCategoryRegisterDto workCategoryRegisterDto) {
        return workCategoryService.update(workCategoryRegisterDto, id);
    }
}
