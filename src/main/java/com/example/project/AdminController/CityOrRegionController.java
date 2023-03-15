package com.example.project.AdminController;

import com.example.project.api.ApiResponse;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.service.Address.CityOrDistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CityOrRegionController {
    private final CityOrDistrictService service;

    @PostMapping("/addCity")
    public ApiResponse<?> addCity(@Validated @RequestBody CityOrDistrictRegisterDto cityOrDistrictRegisterDto) {
        return service.add(cityOrDistrictRegisterDto);
    }
    @GetMapping("/getCityList")
    public ApiResponse<?> getList(){
        return service.getList();
    }
    @DeleteMapping("/deleteCity/{id}")
    public ApiResponse<?> deleteCity(@PathVariable Integer id){
        return service.delete(id);
    }
    @GetMapping("/getCity/{id}")
    public ApiResponse<?> getCity(@PathVariable Integer id){
        return service.getById(id);
    }
    @PutMapping("/updateCity/{id}")
    public ApiResponse<?> updateCity(@PathVariable Integer id, @Validated @RequestBody CityOrDistrictRegisterDto cityOrDistrictRegisterDto){
        return service.update(cityOrDistrictRegisterDto,id);
    }
}
