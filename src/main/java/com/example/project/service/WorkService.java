package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.Work;
import com.example.project.entity.WorkCategory;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.model.WorkCategoryRegisterDto;
import com.example.project.model.WorkRegisterDto;
import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
//import com.example.project.repository.Address.VillageRepository;
import com.example.project.repository.WorkCategoryRepository;
import com.example.project.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkCategoryRepository workCategoryRepository;
    //    private final VillageRepository villageRepository;
    private final CityOrDistrictRepository cityOrDistrictRepository;
    private final ProvinceRepository provinceRepository;
    private final WorkRepository workRepository;


    public ApiResponse add(WorkRegisterDto workRegisterDto) {
        Work save = workRepository.save(workBuilder(workRegisterDto));
        return new ApiResponse<>("Success", 200, save);
    }


    public ApiResponse getList() {
        return new ApiResponse<>(200, workRepository.findAll());
    }


    public ApiResponse delete(Long id) {
        workRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Work not found ")));
        workRepository.deleteById(id);
        return new ApiResponse<>("Successfully deleted", 200);
    }


    public ApiResponse getById(Long id) {
        Work work = workRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Work not found ")));
        return new ApiResponse<>(200, work);
    }

    public ApiResponse update(WorkRegisterDto workRegisterDto, Long id) {
        workRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Work not found ")));
        Work save = workRepository.save(workBuilderUpdate(workRegisterDto, id));
        return new ApiResponse<>("Successfully updated", 200, save);
    }

    private Work workBuilder(WorkRegisterDto workRegisterDto) {
        return Work.builder()
                .workTitle(workRegisterDto.getWorkTitle())
                .workDescription(workRegisterDto.getWorkDescription())
                .startPrice(workRegisterDto.getStartPrice())
                .endPrice(workRegisterDto.getEndPrice())
                .workCategory(workCategoryRepository.getById(workRegisterDto.getWorkCategoryId()))
                .province(provinceRepository.getById(workRegisterDto.getProvinceId()))
                .cityOrDistrict(cityOrDistrictRepository.getById(workRegisterDto.getCityOrDistrictId()))
                .village(workRegisterDto.getVillage())
                .build();
    }

    private Work workBuilderUpdate(WorkRegisterDto workRegisterDto, Long id) {
        return Work.builder()
                .id(id)
                .workTitle(workRegisterDto.getWorkTitle())
                .workDescription(workRegisterDto.getWorkDescription())
                .startPrice(workRegisterDto.getStartPrice())
                .endPrice(workRegisterDto.getEndPrice())
                .workCategory(workCategoryRepository.getById(workRegisterDto.getWorkCategoryId()))
                .province(provinceRepository.getById(workRegisterDto.getProvinceId()))
                .cityOrDistrict(cityOrDistrictRepository.getById(workRegisterDto.getCityOrDistrictId()))
                .village(workRegisterDto.getVillage())
                .build();
    }
}
