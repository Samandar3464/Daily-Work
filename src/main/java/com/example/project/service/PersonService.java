package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.ENUM.Role;
import com.example.project.entity.Person;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.jwtConfig.JwtGenerate;
import com.example.project.model.PersonLoginRequestDto;
import com.example.project.model.PersonRegisterDto;
import com.example.project.model.PersonResponseDto;
import com.example.project.model.TokenResponse;
import com.example.project.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ApiResponse<?> addPerson(PersonRegisterDto personRegisterDto) {
        Optional<Person> byPhoneNumber = personRepository.findByPhoneNumber(personRegisterDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new RecordAlreadyExistException("Username already exist");
        }
        Person person = Person.builder()
                .name(personRegisterDto.getName())
                .phoneNumber(personRegisterDto.getPhoneNumber())
                .password(passwordEncoder.encode(personRegisterDto.getPassword()))
                .role(List.of(Role.USER))
                .build();
        Person save = personRepository.save(person);
        return new ApiResponse<>("Success", 200, new PersonResponseDto(save.getId(), save.getName(), save.getPhoneNumber()));
    }

    public ApiResponse<?> getPersonList() {
        return new ApiResponse<>(200, personRepository.findAll());
    }

    public ApiResponse<?> deletePerson(Integer id) {
        personRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        personRepository.deleteById(id);
        return new ApiResponse<>("User deleted", 200);
    }

    public ApiResponse<?> getById(Integer id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new ApiResponse<>(200, person);
    }

    public ApiResponse<?> login(PersonLoginRequestDto personLoginRequestDto) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(personLoginRequestDto.getPhoneNumber(), personLoginRequestDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        String accessToken = "Bear " + JwtGenerate.generateAccessToken((Person) authenticate.getPrincipal());
        String refreshToken = "RefreshToken " + JwtGenerate.generateRefreshToken((Person) authenticate.getPrincipal());
        return new ApiResponse<>("User login successfully", 200, new TokenResponse(accessToken, refreshToken));
    }
//    public ApiResponse<?> updatePerson(Person update,Integer id){
//        Person person = personRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//    }
}
