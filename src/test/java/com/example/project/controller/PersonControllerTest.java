package com.example.project.controller;

import com.example.project.BaseTestConfiguration;
import com.example.project.entity.ENUM.Gender;
import com.example.project.model.PersonLoginRequestDto;
import com.example.project.model.PersonRegisterDto;
import com.example.project.model.PersonUpdateRequestDto;
import com.example.project.model.Verification;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.model.address.ProvinceRegisterDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static com.example.project.service.PersonService.verificationCode;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PersonControllerTest extends BaseTestConfiguration {
    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    void addPerson() throws Exception {
        add().andExpect(status().isOk());
    }

    @Test
    @DisplayName("Throw exception if phone number already exist")
    void addPersonThrow() throws Exception {
        add();
        add().andExpect(status().isAlreadyReported());
    }

    @Test
    @DisplayName("Throw exception if phone number already exist")
    void addPersonThrowIfCanNotSendSms() throws Exception {
        add();
        add().andExpect(status().isAlreadyReported());
    }

    @Test
    void enablePerson() throws Exception {
        add();
        enable().andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        add();
        enable();
        callLogin().andExpect(status().isOk());
    }

    @Test
    void loginThrowIfUserNotFound() throws Exception {
        callLogin().andExpect(status().isNotFound());
    }
    @Test
    void forgetPassword() throws Exception {
        add();
        forgetPasswordUser().andExpect(status().isOk());
    }

    @Test
    void restoreUserPasswordVerificationCodeChecker() throws Exception {
        add();
        forgetPasswordUser();
        verify().andExpect(status().isOk());
    }

    @Test
    void restoreUserPassword() throws Exception {
        add();
        enable();
        forgetPasswordUser();
        verify();
        changePassword().andExpect(status().isOk());
    }
    @Test
    void getAccessTokenFromRefreshToken() throws Exception {
        add();
        enable();
        callLogin().andExpect(status().isOk());
    }

//    @WithMockUser(roles = "SUPER_ADMIN")
//    void addCityAndProvince() throws Exception {
//        addProvince();
//        addCityDistrict();
//    }
//    @Test
//    void updatePerson() throws Exception {
//        addCityAndProvince();
//        add();
//        enable();
//        callLogin();
//        updateUser().andExpect(status().isOk());
//    }

    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder request =
                post("/api/v1/person/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new PersonRegisterDto("123777777", "User", "123456")));
        return mockMvc.perform(request);
    }

    private ResultActions callLogin() throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/person/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new PersonLoginRequestDto("123777777", "123456")));
        return mockMvc.perform(request);
    }

    private ResultActions enable() throws Exception {
        final MockHttpServletRequestBuilder request =
                post("/api/v1/person/enablePerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new Verification("123777777", verificationCode)));
        return mockMvc.perform(request);
    }

    private ResultActions forgetPasswordUser() throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/person/forgetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new Verification("123777777")));
        return mockMvc.perform(request);
    }

    private ResultActions changePassword() throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/person/change/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new PersonRegisterDto("123777777", "123456")));
        return mockMvc.perform(request);
    }

    private ResultActions verify() throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/person/verifyCode")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new Verification("123777777", verificationCode)));
        return mockMvc.perform(request);
    }


//    private ResultActions updateUser() throws Exception {
//        final MockHttpServletRequestBuilder request =
//                put("/api/v1/person/updatePerson")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(
//                                person()));
//        return mockMvc.perform(request);
//    }
//
//    private PersonUpdateRequestDto person(){
//       return PersonUpdateRequestDto.builder()
//               .name("Someone")
//               .surname("Someone")
//               .age((short) 20)
//               .gender(Gender.MALE)
//               .aboutMe("something")
//               .provinceId(1)
//               .cityOrDistrictId(1)
//               .village("somewhere")
//               .homeAddress("somewhere")
//               .build();
//    }
//    private ResultActions addProvince() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/addProvince")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
//        return mockMvc.perform(requestBuilder);
//    }
//    private ResultActions addCityDistrict() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/addCity")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new CityOrDistrictRegisterDto("Tashkent sh",1)));
//        return mockMvc.perform(requestBuilder);
//    }
}