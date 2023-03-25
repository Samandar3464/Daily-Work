//package com.example.project.controller;
//
//import com.example.project.BaseTestConfiguration;
//import com.example.project.model.*;
//import com.example.project.model.address.CityOrDistrictRegisterDto;
//import com.example.project.model.address.ProvinceRegisterDto;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//import static com.example.project.service.PersonService.verificationCode;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//class WorkControllerTest extends BaseTestConfiguration {
//    @AfterEach
//    void termDown(){
//        workRepository.deleteAll();
//    }
//    @Test
//    @WithMockUser(roles = "SUPER_ADMIN")
//    void addCityAndProvince() throws Exception {
//        addProvince();
//        addCityDistrict();
//        addCategory();
//    }
//
//    @Test
//    void addWork() throws Exception {
//        addCityAndProvince();
//        addUser();
//        enableUser();
//        login();
//        add().andExpect(status().isOk());
//    }
//
////    @Test
////    void getPersonWorkList() {
////    }
////
////    @Test
////    void getWorkById() {
////    }
////
////    @Test
////    void deletePersonWorkById() {
////    }
////
////    @Test
////    void updateWork() {
////    }
//
//    private ResultActions add() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/work/addWork")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(createWork()));
//        return mockMvc.perform(requestBuilder);
//    }
//    private WorkRegisterDto createWork(){
//        return WorkRegisterDto.builder()
//                .workTitle("Remont")
//                .workDescription("Remont")
//                .startPrice(100.0)
//                .endPrice(1000.0)
//                .workCategoryId(1)
//                .provinceId(1)
//                .cityOrDistrictId(1)
//                .village("qarshi")
//                .build();
//    }
//    private ResultActions callDelete(int id) throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                delete("/api/v1/admin/work/deleteById/" + id);
//        return mockMvc.perform(requestBuilder);
//    }
//
//    private ResultActions addCityDistrict() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/addCity")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new CityOrDistrictRegisterDto("Tashkent sh",1)));
//        return mockMvc.perform(requestBuilder);
//    }
//    private ResultActions addCategory() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/category/admin/addCategory")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new WorkCategoryRegisterDto("Qurulish")));
//        return mockMvc.perform(requestBuilder);
//    }
//    private ResultActions addProvince() throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                post("/api/v1/addProvince")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
//        return mockMvc.perform(requestBuilder);
//    }
//    private ResultActions addUser() throws Exception {
//        final MockHttpServletRequestBuilder request =
//                post("/api/v1/person/addPerson")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(
//                                new PersonRegisterDto("123777777", "User", "123456")));
//        return mockMvc.perform(request);
//    }
//    private ResultActions enableUser() throws Exception {
//        final MockHttpServletRequestBuilder request =
//                post("/api/v1/person/enablePerson")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(
//                                new Verification("123777777", verificationCode)));
//        return mockMvc.perform(request);
//    }
//    private ResultActions login() throws Exception {
//        final MockHttpServletRequestBuilder request =
//                get("/api/v1/person/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(
//                                new PersonLoginRequestDto("123777777", "123456")));
//        return mockMvc.perform(request);
//    }
//}