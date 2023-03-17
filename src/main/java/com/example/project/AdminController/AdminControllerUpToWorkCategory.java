package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.WorkCategoryRegisterDto;
import com.example.project.service.WorkCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category/admin")
public class AdminControllerUpToWorkCategory {
    private final WorkCategoryService workCategoryService;

    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> addCategory(@Validated @RequestBody WorkCategoryRegisterDto workCategoryRegisterDto) {
        return workCategoryService.add(workCategoryRegisterDto);
    }

    @GetMapping("/getCategoryList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getCategoryList() {
        return workCategoryService.getList();
    }

    @GetMapping("/getCategoryById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getCategoryById(@PathVariable Integer id) {
        return workCategoryService.getById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> deleteCategoryById(@PathVariable Integer id) {
        return workCategoryService.delete(id);
    }

    @PutMapping("/updateCategory/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> updateCategory(@PathVariable Integer id, @RequestBody WorkCategoryRegisterDto workCategoryRegisterDto) {
        return workCategoryService.update(workCategoryRegisterDto, id);
    }
}
