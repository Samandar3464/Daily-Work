package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.address.ProvinceRegisterDto;
import com.example.project.service.Address.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminControllerUpToProvince {
    private final ProvinceService provinceService;

    @PostMapping("/addProvince")
    public ApiResponse<?> addProvince(@Validated @RequestBody ProvinceRegisterDto provinceRegisterDto) {
        return provinceService.add(provinceRegisterDto);
    }
    @GetMapping("/getProvinceList")
    public ApiResponse<?> getList(){
        return provinceService.getList();
    }
    @DeleteMapping("/deleteProvince/{id}")
    public ApiResponse<?> deleteProvince(@PathVariable Integer id){
        return provinceService.delete(id);
    }
    @GetMapping("/getProvince/{id}")
    public ApiResponse<?> getProvince(@PathVariable Integer id){
        return provinceService.getById(id);
    }
    @PutMapping("/updateProvince/{id}")
    public ApiResponse<?> updateProvince(@PathVariable Integer id, @Validated @RequestBody ProvinceRegisterDto provinceRegisterDto){
        return provinceService.update(provinceRegisterDto,id);
    }
}
