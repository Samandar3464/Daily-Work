package com.example.project.model;

import com.example.project.entity.ENUM.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChange {
    @Enumerated(EnumType.STRING)
    private Role newRole;

    private Integer personId;
}
