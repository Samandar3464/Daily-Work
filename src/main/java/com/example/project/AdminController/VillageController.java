//package com.example.project.AdminController;
//
//import com.example.project.api.ApiResponse;
//import com.example.project.model.address.VillageRegisterDto;
//import com.example.project.service.Address.VillageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("api/v1")
//public class VillageController {
//    private final VillageService villageService;
//
//    @PostMapping("/addVillage")
//    public ApiResponse<?> addVillage(@Validated @RequestBody VillageRegisterDto villageRegisterDto) {
//        return villageService.add(villageRegisterDto);
//    }
//    @GetMapping("/getVillageList")
//    public ApiResponse<?> getList(){
//        return villageService.getList();
//    }
//    @DeleteMapping("/deleteVillage/{id}")
//    public ApiResponse<?> deleteVillage(@PathVariable Integer id){
//        return villageService.delete(id);
//    }
//    @GetMapping("/getVillage/{id}")
//    public ApiResponse<?> getVillage(@PathVariable Integer id){
//        return villageService.getById(id);
//    }
//    @PutMapping("/updateVillage/{id}")
//    public ApiResponse<?> updateVillage(@PathVariable Integer id, @Validated @RequestBody VillageRegisterDto villageRegisterDto){
//        return villageService.update(villageRegisterDto,id);
//    }
//}
