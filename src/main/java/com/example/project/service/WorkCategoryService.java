package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.WorkCategory;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.RecordNotFoundException;
import com.example.project.model.WorkCategoryRegisterDto;
import com.example.project.repository.WorkCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkCategoryService implements BaseService<ApiResponse, WorkCategoryRegisterDto> {

    private final WorkCategoryRepository workCategoryRepository;


    @Override
    public ApiResponse add(WorkCategoryRegisterDto workCategoryRegisterDto) {
        Optional<WorkCategory> byName = workCategoryRepository.findByName(workCategoryRegisterDto.getName());
        if (byName.isPresent()) {
            throw new RecordAlreadyExistException("This category already have into this city");
        }
        WorkCategory workCategory = new WorkCategory();
        workCategory.setName(workCategoryRegisterDto.getName());
        WorkCategory save = workCategoryRepository.save(workCategory);
        return new ApiResponse<>("Success", 200, save);
    }

    @Override
    public ApiResponse getList() {
        return new ApiResponse<>(200, workCategoryRepository.findAll());
    }

    @Override
    public ApiResponse delete(Integer id) {
        workCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        workCategoryRepository.deleteById(id);
        return new ApiResponse<>("Successfully deleted", 200);
    }

    @Override
    public ApiResponse getById(Integer id) {
        WorkCategory workCategory = workCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        return new ApiResponse<>(200, workCategory);
    }

    @Override
    public ApiResponse update(WorkCategoryRegisterDto workCategoryRegisterDto, Integer id) {
        WorkCategory workCategory = workCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        workCategory.setName(workCategoryRegisterDto.getName());
        WorkCategory save = workCategoryRepository.save(workCategory);
        return new ApiResponse<>("Successfully updated", 200, save);
    }
}
