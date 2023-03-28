package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/work")
public class AdminControllerUpToWork {
    private final WorkService workService;
    @GetMapping("/getWorkList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getWorkList(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int size,
                                      @RequestParam(defaultValue = "name") String sort) {
        return workService.getList(page, size, sort);
    }
    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> deleteWorkById(@PathVariable Long id) {
        return workService.delete(id);
    }
}
