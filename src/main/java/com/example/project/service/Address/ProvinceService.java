package com.example.project.service.Address;

import com.example.project.api.ApiResponse;
import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
//import com.example.project.entity.address.Village;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.model.address.ProvinceRegisterDto;
import com.example.project.repository.Address.ProvinceRepository;
import com.example.project.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvinceService implements BaseService<Province , ProvinceRegisterDto> {
    private final ProvinceRepository provinceRepository;

    @Override
    public ApiResponse add(ProvinceRegisterDto provinceRegisterDto) {
        if (provinceRepository.findByName(provinceRegisterDto.getName()).isPresent()){
            throw  new RecordAlreadyExistException("Province already exist");
        }
        Province province = new Province();
        province.setName(provinceRegisterDto.getName());
        Province save = provinceRepository.save(province);
        return new ApiResponse<>("Successfully updated", 200, save);
    }

    @Override
    public ApiResponse getList() {
        return new ApiResponse<>(200, provinceRepository.findAll());
    }

    @Override
    public ApiResponse delete(Integer id) {
        provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        provinceRepository.deleteById(id);
        return new ApiResponse<>("Successfully deleted" , 200);
    }

    @Override
    public ApiResponse getById(Integer id) {
        Province province = provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        return new ApiResponse<>(200, province);
    }

    @Override
    public ApiResponse update(ProvinceRegisterDto provinceRegisterDto, Integer id) {
        if (provinceRepository.findByName(provinceRegisterDto.getName()).isPresent()){
            throw  new RecordAlreadyExistException("Province already exist");
        }
        Province province = provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        province.setName(provinceRegisterDto.getName());
        Province save = provinceRepository.save(province);
        return new ApiResponse<>("Successfully updated", 200, save);
    }
}
