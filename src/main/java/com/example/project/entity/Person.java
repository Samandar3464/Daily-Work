package com.example.project.entity;

import com.example.project.entity.ENUM.Gender;
import com.example.project.entity.ENUM.Role;
import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
//import com.example.project.entity.address.Village;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = "^[A-Za-z]*$")
    private String name;
    @Pattern(regexp = "^[A-Za-z]*$")
    private String surname;
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 6)
    private String password;
    @Email
    private String email;
    @NotBlank
    @Size(min = 9)
    private String phoneNumber;
    private short age;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private List<Role> role;

    @ManyToOne
    private Province province;
    @ManyToOne
    private CityOrDistrict cityOrDistrict;
    //    @ManyToOne
//    private Village village;
    String village;
    private String homeAddress;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        role.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
