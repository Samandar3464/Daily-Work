package com.example.project.api;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String massage;
    private int statusCode;
    private T data;

    public ApiResponse(String massage, int statusCode) {
        this.massage = massage;
        this.statusCode = statusCode;
    }

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}