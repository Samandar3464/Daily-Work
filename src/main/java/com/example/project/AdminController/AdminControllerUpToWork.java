package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/work")
public class AdminControllerUpToWork {
    private final WorkService workService;
    @GetMapping("/getWorkList")
    public ApiResponse<?> getWorkList() {
        return workService.getList();
    }
    @DeleteMapping("/deleteById/{id}")
    public ApiResponse<?> deleteWorkById(@PathVariable Long id) {
        return workService.delete(id);
    }
}
