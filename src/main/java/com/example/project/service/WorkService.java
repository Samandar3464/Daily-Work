package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.Person;
import com.example.project.entity.Work;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.model.PersonResponseDto;
import com.example.project.model.WorkRegisterDto;
import com.example.project.model.WorkResponseDto;
import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
//import com.example.project.repository.Address.VillageRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.repository.WorkCategoryRepository;
import com.example.project.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkCategoryRepository workCategoryRepository;
    //    private final VillageRepository villageRepository;
    private final CityOrDistrictRepository cityOrDistrictRepository;
    private final ProvinceRepository provinceRepository;
    private final WorkRepository workRepository;
    private final PersonRepository personRepository;

    public ApiResponse add(WorkRegisterDto workRegisterDto) {
        Work save = workRepository.save(workBuilder(workRegisterDto));
        Person person = save.getPerson();
        WorkResponseDto workResponseDto = WorkResponseDto.builder()
                .id(save.getId())
                .workTitle(save.getWorkTitle())
                .workDescription(save.getWorkDescription())
                .workCategoryName(save.getWorkCategory().getName())
                .startPrice(save.getStartPrice())
                .endPrice(save.getEndPrice())
                .createdTime(save.getCreatedTime())
                .provinceName(save.getProvince().getName())
                .cityOrDistrictName(save.getCityOrDistrict().getName())
                .village(save.getVillage())
                .personResponseDto(new PersonResponseDto(person.getId(), person.getUsername(), person.getPhoneNumber()))
                .build();

        return new ApiResponse<>("Success", 200, workResponseDto);
    }


    public ApiResponse getList() {
        return new ApiResponse<>(200, workRepository.findAll());
    }

    public ApiResponse getPersonWorksList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        Person person = personRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new ApiResponse<>(200, workRepository.findAllByPersonId(person.getId()));
    }

    public ApiResponse delete(Long id) {
        workRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Work not found ")));
        workRepository.deleteById(id);
        return new ApiResponse<>("Successfully deleted", 200);
    }

    public ApiResponse deletePersonWork(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        Optional<Person> person = personRepository.findByPhoneNumber(phoneNumber);
        Work work = workRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Work not found ")));
        if (work.getPerson().getId()==person.get().getId()){
            workRepository.deleteById(id);
        }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String principal = (String) authentication.getPrincipal();
        Optional<Person> byPhoneNumber = personRepository.findByPhoneNumber(principal);
        return Work.builder()
                .workTitle(workRegisterDto.getWorkTitle())
                .workDescription(workRegisterDto.getWorkDescription())
                .startPrice(workRegisterDto.getStartPrice())
                .endPrice(workRegisterDto.getEndPrice())
                .workCategory(workCategoryRepository.getById(workRegisterDto.getWorkCategoryId()))
                .province(provinceRepository.getById(workRegisterDto.getProvinceId()))
                .cityOrDistrict(cityOrDistrictRepository.getById(workRegisterDto.getCityOrDistrictId()))
                .createdTime(LocalDate.now())
                .person(byPhoneNumber.get())
                .village(workRegisterDto.getVillage())
                .build();
    }

    private Work workBuilderUpdate(WorkRegisterDto workRegisterDto, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        Person person = (Person) authentication.getPrincipal();
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
                .person(person)
                .build();
    }
}
