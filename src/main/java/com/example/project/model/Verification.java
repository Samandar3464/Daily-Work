package com.example.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Verification {
    private String phoneNumber;
    private int code;

    public Verification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
