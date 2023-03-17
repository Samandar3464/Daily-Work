package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.service.Address.CityOrDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AminControllerUpToCityOrRegion {
    private final CityOrDistrictService service;

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> addCity(@Validated @RequestBody CityOrDistrictRegisterDto cityOrDistrictRegisterDto) {
        return service.add(cityOrDistrictRegisterDto);
    }
    @GetMapping("/getCityList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getList(){
        return service.getList();
    }
    @DeleteMapping("/deleteCity/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> deleteCity(@PathVariable Integer id){
        return service.delete(id);
    }
    @GetMapping("/getCity/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> getCity(@PathVariable Integer id){
        return service.getById(id);
    }
    @PutMapping("/updateCity/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<?> updateCity(@PathVariable Integer id, @Validated @RequestBody CityOrDistrictRegisterDto cityOrDistrictRegisterDto){
        return service.update(cityOrDistrictRegisterDto,id);
    }
}
