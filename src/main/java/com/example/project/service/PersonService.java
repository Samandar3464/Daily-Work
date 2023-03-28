package com.example.project.service;

import com.example.project.api.ApiResponse;
import com.example.project.entity.ENUM.Role;
import com.example.project.entity.Person;
import com.example.project.exception.RecordAlreadyExistException;
import com.example.project.exception.RefreshTokeNotFound;
import com.example.project.exception.TimeExceededException;
import com.example.project.exception.UserNotFoundException;
import com.example.project.jwtConfig.JwtGenerate;
import com.example.project.model.*;
import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
import com.example.project.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final ProvinceRepository provinceRepository;
    private final CityOrDistrictRepository cityOrDistrictRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SmsSendingService smsSendingService;
    public static int verificationCode;
//    Sms sending ishlaydigon versiyasi asosiy method

    //    public ApiResponse<?> addPerson(PersonRegisterDto personRegisterDto) {
//        Optional<Person> byPhoneNumber = personRepository.findByPhoneNumber(personRegisterDto.getPhoneNumber());
//        if (byPhoneNumber.isPresent()) {
//            throw new RecordAlreadyExistException("Username already exist");
//        }
//        verificationCode = verificationCodeGenerator();
//        System.out.println(verificationCode);
//        String massageResponse = smsSendingService.verificationCode(personRegisterDto.getPhoneNumber(), verificationCode);
//        if (massageResponse.equals("queued")) {
//            Person person = Person.builder()
//                    .name(personRegisterDto.getName())
//                    .phoneNumber(personRegisterDto.getPhoneNumber())
//                    .password(passwordEncoder.encode(personRegisterDto.getPassword()))
//                    .role(List.of(Role.USER))
//                    .codeVerification(verificationCode)
//                    .build();
//            personRepository.save(person);
//            return new ApiResponse<>("Success", 200, "Code sent to your phone number ");
//        }
//
//        return new ApiResponse<>("Fail", 400, "Can not send verification code");
//    }

//    Sms sending olib tashlangan versiyasi
    public ApiResponse<?> addPerson(PersonRegisterDto personRegisterDto) {
        Optional<Person> byPhoneNumber = personRepository.findByPhoneNumber(personRegisterDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new RecordAlreadyExistException("Username already exist");
        }
        verificationCode = verificationCodeGenerator();
        System.out.println(verificationCode);
            Person person = Person.builder()
                    .name(personRegisterDto.getName())
                    .phoneNumber(personRegisterDto.getPhoneNumber())
                    .password(passwordEncoder.encode(personRegisterDto.getPassword()))
                    .role(List.of(Role.USER))
                    .codeVerification(verificationCode)
                    .build();
            personRepository.save(person);
            return new ApiResponse<>("Success", 200, "Code sent to your phone number ");
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
       try {
           Authentication authentication = new UsernamePasswordAuthenticationToken(personLoginRequestDto.getPhoneNumber(), personLoginRequestDto.getPassword());
           Authentication authenticate = authenticationManager.authenticate(authentication);
           String accessToken = "Bearer " + JwtGenerate.generateAccessToken((Person) authenticate.getPrincipal());
           String refreshToken = "RefreshToken " + JwtGenerate.generateRefreshToken((Person) authenticate.getPrincipal());
           return new ApiResponse<>("User login successfully", 200, new TokenResponse(accessToken, refreshToken));
       }catch (BadCredentialsException e){
           throw new UserNotFoundException("USer not fount");
       }

    }

    public ApiResponse<?> getAccessToken(HttpServletRequest request) {
        return new ApiResponse<>(200, checkRefreshTokenValidAndGetAccessToken(request));
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
// SMS Jonatadi telga asosiy method
//    public ApiResponse<?> generateCodeForForgetPassword(Verification phoneNumber) {
//        Person person = personRepository.findByPhoneNumber(phoneNumber.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
//        String massageResponse = smsSendingService.verificationCode(phoneNumber.getPhoneNumber(), verificationCode);
//        if (massageResponse.equals("queued")) {
//            verificationCode = verificationCodeGenerator();
//            person.setCodeVerification(verificationCode);
//            System.out.println(verificationCode);
//            personRepository.save(person);
//            return new ApiResponse<>("Verification code sent to your phone number ", 200);
//        }
//        return new ApiResponse<>("Can not send verification code ", 400);
//    }

    public ApiResponse<?> generateCodeForForgetPassword(Verification phoneNumber) {
        Person person = personRepository.findByPhoneNumber(phoneNumber.getPhoneNumber()).orElseThrow(() -> new UserNotFoundException("User not found"));
            verificationCode = verificationCodeGenerator();
            person.setCodeVerification(verificationCode);
            System.out.println(verificationCode);
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

    public ApiResponse<?> verifyCodeCheckerForRestorePassword(Verification verification) {
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

    public ApiResponse<?> updatePerson(PersonUpdateRequestDto update) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new UserNotFoundException("User not found");
        }
        String phoneNumber = (String) authentication.getPrincipal();
        Person person = personRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User not found"));
        Person updatedPerson = updatePerson(update, person);
        Person save = personRepository.save(updatedPerson);

        return new ApiResponse<>(200, new PersonResponseDto().of(save));
    }

    private int verificationCodeGenerator() {
        return RandomGenerator.getDefault().nextInt(1000, 9999);
    }

    private Person updatePerson(PersonUpdateRequestDto update, Person person) {
        person.setName(update.getName());
        person.setSurname(update.getSurname());
        person.setAge(update.getAge());
        person.setGender(update.getGender());
        person.setAboutMe(update.getAboutMe());
        person.setProvince(provinceRepository.getById(update.getProvinceId()));
        person.setCityOrDistrict(cityOrDistrictRepository.getById(update.getCityOrDistrictId()));
        person.setVillage(update.getVillage());
        person.setHomeAddress(update.getHomeAddress());
        return person;
    }

    private String checkRefreshTokenValidAndGetAccessToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader == null || !requestHeader.startsWith("RefreshToken")) {
            throw new RefreshTokeNotFound("ReFresh token not found");
        }
        String token = requestHeader.replace("RefreshToken ", "");
        Claims claims = JwtGenerate.isValidRefreshToken(token);
        if (claims == null) {
            throw new TimeExceededException("ReFresh token valid time end");
        }
        Person person = personRepository.findByPhoneNumber(claims.getSubject()).orElseThrow(() -> new UserNotFoundException("User not found"));
        String accessToken = "Bear " + JwtGenerate.generateAccessToken(person);
        return accessToken;
    }

}
