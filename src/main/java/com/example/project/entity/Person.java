package com.example.project.entity;

import com.example.project.entity.ENUM.Gender;
import com.example.project.entity.ENUM.Role;
import com.example.project.entity.address.CityOrDistrict;
import com.example.project.entity.address.Province;
//import com.example.project.entity.address.Village;
import com.example.project.model.PersonUpdateRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Size(min = 6)
    private String password;
    @NotBlank
    @Size(min = 9)
    private String phoneNumber;
    private short age;
    private String aboutMe;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private List<Role> role;

    @ManyToOne
    private Province province;
    @ManyToOne
    private CityOrDistrict cityOrDistrict;
    @JsonIgnore
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Work> works;
    //    @ManyToOne
//    private Village village;
    private String village;
    private String homeAddress;
    private boolean enableUser = false;
    private int codeVerification;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        role.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return roles;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnoreProperties
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnoreProperties
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
        return enableUser;
    }

}