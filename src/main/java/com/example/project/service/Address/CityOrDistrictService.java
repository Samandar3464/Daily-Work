package com.example.project.service.Address;

import com.example.project.api.ApiResponse;
import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
import com.example.project.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityOrDistrictService implements BaseService<CityOrDistrict, CityOrDistrictRegisterDto> {
    private final CityOrDistrictRepository cityOrDistrictRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public ApiResponse add(CityOrDistrictRegisterDto cityOrDistrictRegisterDto) {
        Optional<CityOrDistrict> byName = cityOrDistrictRepository.findByName(cityOrDistrictRegisterDto.getName());
        if (byName.isPresent() && byName.get().getProvince().getId() == cityOrDistrictRegisterDto.getProvinceId()) {
            throw new RecordAlreadyExistException("This province already have into this province");
        }
        Optional<Province> provinceOptional = provinceRepository.findById(cityOrDistrictRegisterDto.getProvinceId());
        if (provinceOptional.isEmpty()) {
            throw new RecordNotFoundException("Province not found");
        }
        Province province = provinceOptional.get();
        CityOrDistrict cityOrDistrict = new CityOrDistrict();
        cityOrDistrict.setName(cityOrDistrictRegisterDto.getName());
        cityOrDistrict.setProvince(province);
        CityOrDistrict save = cityOrDistrictRepository.save(cityOrDistrict);
        return new ApiResponse<>("Success", 200, save);
    }

    @Override
    public ApiResponse getList() {
        return new ApiResponse<>(200, cityOrDistrictRepository.findAll());
    }

    @Override
    public ApiResponse delete(Integer id) {
        cityOrDistrictRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("City not found ")));
        cityOrDistrictRepository.deleteById(id);
        return new ApiResponse<>("Successfully deleted", 200);
    }

    @Override
    public ApiResponse getById(Integer id) {
        CityOrDistrict cityOrDistrict = cityOrDistrictRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Village not found ")));
        return new ApiResponse<>(200, cityOrDistrict);
    }

    @Override
    public ApiResponse update(CityOrDistrictRegisterDto cityOrDistrictRegisterDto, Integer id) {
        CityOrDistrict cityOrDistrict = cityOrDistrictRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("City not found ")));
        Province province = provinceRepository.findById(cityOrDistrictRegisterDto.getProvinceId()).orElseThrow(() -> new RecordNotFoundException("City not found"));
        cityOrDistrict.setName(cityOrDistrictRegisterDto.getName());
        cityOrDistrict.setProvince(province);
        CityOrDistrict save = cityOrDistrictRepository.save(cityOrDistrict);
        return new ApiResponse<>("Successfully updated", 200, save);
    }
}
