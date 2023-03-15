//package com.example.project.service.Address;
//
//import com.example.project.api.ApiResponse;
//import com.example.project.entity.address.CityOrDistrict;
//import com.example.project.entity.address.Village;
//import com.example.project.exception.RecordAlreadyExistException;
//import com.example.project.exception.RecordNotFoundException;
//import com.example.project.model.address.VillageRegisterDto;
//import com.example.project.repository.Address.CityOrDistrictRepository;
//import com.example.project.repository.Address.VillageRepository;
//import com.example.project.service.BaseService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class VillageService implements BaseService<ApiResponse<Village>, VillageRegisterDto> {
//    private final VillageRepository villageRepository;
//    private final CityOrDistrictRepository cityOrDistrictRepository;
//
//    @Override
//    public ApiResponse add(VillageRegisterDto villageRegisterDto) {
//        Optional<Village> byName = villageRepository.findByName(villageRegisterDto.getName());
//        if (byName.isPresent() && byName.get().getCityOrDistrict().getId() == villageRegisterDto.getCityOrDistrictId()) {
//            throw new RecordAlreadyExistException("This village already have into this city");
//        }
//        Optional<CityOrDistrict> city = cityOrDistrictRepository.findById(villageRegisterDto.getCityOrDistrictId());
//        if (city.isEmpty()) {
//            throw new RecordNotFoundException("City not found");
//        }
//        CityOrDistrict cityOrDistrict = city.get();
//        Village village = new Village();
//        village.setCityOrDistrict(cityOrDistrict);
//        village.setName(villageRegisterDto.getName());
//        Village save = villageRepository.save(village);
//        return new ApiResponse<>("Success", 200, save);
//    }
//
//    @Override
//    public ApiResponse getList() {
//        return new ApiResponse<>(200, villageRepository.findAll());
//    }
//
//    @Override
//    public ApiResponse delete(Integer id) {
//        villageRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Village not found ")));
//        villageRepository.deleteById(id);
//        return new ApiResponse<>("Successfully deleted" , 200);
//    }
//
//    @Override
//    public ApiResponse getById(Integer id) {
//        Village village = villageRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Village not found ")));
//        return new ApiResponse<>(200, village);
//    }
//
//    @Override
//    public ApiResponse update(VillageRegisterDto villageRegisterDto, Integer id) {
//        Village village = villageRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Village not found ")));
//        CityOrDistrict city = cityOrDistrictRepository.findById(villageRegisterDto.getCityOrDistrictId()).orElseThrow(() -> new RecordNotFoundException("City not found"));
//        village.setName(villageRegisterDto.getName());
//        village.setCityOrDistrict(city);
//        Village save = villageRepository.save(village);
//        return new ApiResponse<>("Successfully updated", 200, save);
//    }
//
//}
