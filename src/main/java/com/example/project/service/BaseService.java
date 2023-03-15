package com.example.project.service;

import com.example.project.api.ApiResponse;

import java.util.List;

public interface BaseService<T, R> {
    ApiResponse<T> add(R r);
    ApiResponse<T> getList();
    ApiResponse<T> delete(Integer id);
    ApiResponse<T> getById(Integer id);
    ApiResponse<T> update(R r , Integer id);
}
