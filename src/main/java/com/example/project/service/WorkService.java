package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.Person;
import com.example.project.entity.Work;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.model.PersonResponseDto;
import com.example.project.model.PersonResponseDtoForWork;
import com.example.project.model.WorkRegisterDto;
import com.example.project.model.WorkResponseDto;
import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
//import com.example.project.repository.Address.VillageRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.repository.WorkCategoryRepository;
import com.example.project.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
                .personResponseDtoForWork(new PersonResponseDtoForWork(person.getId(), person.getUsername(), person.getPhoneNumber()))
                .build();

        return new ApiResponse<>("Success", 200, workResponseDto);
    }


    public ApiResponse getList(int page, int size, String sort) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        Page<Work> all = workRepository.findAll(pageable);
        return new ApiResponse<>(200,workResponseBuilder(all));
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
        if (work.getPerson().getId() == person.get().getId()) {
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
        String phone = (String) authentication.getPrincipal();
        Person person = personRepository.findByPhoneNumber(phone).orElseThrow(() -> new UserNotFoundException("User not found"));

        return Work.builder()
                .workTitle(workRegisterDto.getWorkTitle())
                .workDescription(workRegisterDto.getWorkDescription())
                .startPrice(workRegisterDto.getStartPrice())
                .endPrice(workRegisterDto.getEndPrice())
                .workCategory(workCategoryRepository.getById(workRegisterDto.getWorkCategoryId()))
                .province(provinceRepository.getById(workRegisterDto.getProvinceId()))
                .cityOrDistrict(cityOrDistrictRepository.getById(workRegisterDto.getCityOrDistrictId()))
                .createdTime(LocalDate.now())
                .person(person)
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

    private List<WorkResponseDto> workResponseBuilder(Page<Work> work){
        return    work.stream().map(
                work1 -> {
                    return WorkResponseDto.builder()
                            .id(work1.getId())
                            .workTitle(work1.getWorkTitle())
                            .workDescription(work1.getWorkDescription())
                            .startPrice(work1.getStartPrice())
                            .endPrice(work1.getEndPrice())
                            .createdTime(work1.getCreatedTime())
                            .workCategoryName(work1.getWorkCategory().getName())
                            .provinceName(work1.getProvince().getName())
                            .cityOrDistrictName(work1.getCityOrDistrict().getName())
                            .village(work1.getVillage())
                            .personResponseDtoForWork(
                                    new PersonResponseDtoForWork(
                                            work1.getPerson().getId(),
                                            work1.getPerson().getName()
                                            , work1.getPerson().getPhoneNumber()))
                            .build();
                }
        ).toList();
    }
}
