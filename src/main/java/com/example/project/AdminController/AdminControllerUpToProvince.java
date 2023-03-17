package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.address.ProvinceRegisterDto;
import com.example.project.service.Address.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminControllerUpToProvince {
    private final ProvinceService provinceService;

    @PostMapping("/addProvince")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> addProvince(@Validated @RequestBody ProvinceRegisterDto provinceRegisterDto) {
        return provinceService.add(provinceRegisterDto);
    }
    @GetMapping("/getProvinceList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getList(){
        return provinceService.getList();
    }
    @DeleteMapping("/deleteProvince/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> deleteProvince(@PathVariable Integer id){
        return provinceService.delete(id);
    }
    @GetMapping("/getProvince/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getProvince(@PathVariable Integer id){
        return provinceService.getById(id);
    }
    @PutMapping("/updateProvince/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> updateProvince(@PathVariable Integer id, @Validated @RequestBody ProvinceRegisterDto provinceRegisterDto){
        return provinceService.update(provinceRegisterDto,id);
    }
}
