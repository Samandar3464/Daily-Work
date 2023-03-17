package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.ENUM.Role;
import com.example.project.entity.Person;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.jwtConfig.JwtGenerate;
import com.example.project.model.*;
import com.example.project.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

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
        int codeGenerator = verificationCodeGenerator();
        System.out.println(codeGenerator);
        Person person = Person.builder()
                .name(personRegisterDto.getName())
                .phoneNumber(personRegisterDto.getPhoneNumber())
                .password(passwordEncoder.encode(personRegisterDto.getPassword()))
                .role(List.of(Role.USER))
                .codeVerification(codeGenerator)
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

    public ApiResponse<?> changePersonRole(RoleChange roleChange) {
        Person person = personRepository.findById(roleChange.getPersonId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (person.getRole().contains(roleChange.getNewRole())) {
            throw new RecordAlreadyExistException("This role already have ");
        }
        person.getRole().add(roleChange.getNewRole());
        personRepository.save(person);
        return new ApiResponse<>(String.format("Role changed %s", roleChange.getNewRole()), 200);
    }

    public ApiResponse<?> forgetPassword(Verification phoneNumber) {
//        Optional<Person> byPhoneNumber = personRepository.findByPhoneNumber(phoneNumber);
//        if (!byPhoneNumber.isPresent()){
//            throw  new UserNotFoundException("User not found");
//        }
//        Person person = byPhoneNumber.get();
        Person person = personRepository.findByPhoneNumber(phoneNumber.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        int code = verificationCodeGenerator();
        person.setCodeVerification(code);
        System.out.println(code);
        personRepository.save(person);
        return new ApiResponse<>("Verification code sent to your phone number ", 200);
    }

    public ApiResponse<?> verifyCodeForEnablePerson(Verification verification) {
        Person person = personRepository.findByPhoneNumber(verification.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (person.getCodeVerification() == verification.getCode()) {
            person.setEnableUser(true);
            person.setCodeVerification(0);
            personRepository.save(person);
        }
        return new ApiResponse<>("User verified successfully", 200);
    }

    public ApiResponse<?> verifyCodeForRestorePassword(Verification verification) {
        Person person = personRepository.findByPhoneNumber(verification.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (person.getCodeVerification() == verification.getCode()) {
            person.setCodeVerification(0);
            personRepository.save(person);
        }
        return new ApiResponse<>("User verified successfully", 200);
    }

    public ApiResponse<?> changePassword(PersonRegisterDto personRegisterDto) {
        Person person = personRepository.findByPhoneNumber(personRegisterDto.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
        person.setPassword(passwordEncoder.encode(personRegisterDto.getPassword()));
        personRepository.save(person);
        return new ApiResponse<>("Password  successfully changed", 200);
    }

    private int verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(1000, 9999);
    }

//    public ApiResponse<?> updatePerson(Person update,Integer id){
//        Person person = personRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
//    }

}
