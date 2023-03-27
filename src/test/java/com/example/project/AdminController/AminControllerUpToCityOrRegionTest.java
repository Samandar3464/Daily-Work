package com.example.project.AdminController;


import com.example.project.BaseTestConfiguration;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.model.address.ProvinceRegisterDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AminControllerUpToCityOrRegionTest extends BaseTestConfiguration {

    @AfterEach
    void tearDown() {
        cityOrDistrictRepository.deleteAll();
//        provinceRepository.deleteAll();
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Can add City or District ")
    void addCity() throws Exception {
        addCityDistrict().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Check add City throw exception if city exist ")
    void addCityThrow() throws Exception {
        addProvince();
        addCityDistrict();
        addCityDistrict().andExpect(status().isAlreadyReported());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityList() throws Exception {
        addCityDistrict();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCity() throws Exception {
        addCityDistrict();
        callDelete(6).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteCityThrow() throws Exception {
        callDelete(999).andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityById() throws Exception {
        addCityDistrict();
        getById(4).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getCityByIdThrow() throws Exception {
        getById(999).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void updateCity() throws Exception {
        addCityDistrict();
        update(3).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    @DisplayName("Throw exception if city not fount")
    void updateCityThrow() throws Exception {
        update(100).andExpect(status().isNotFound());
    }
//    @Test
//    @WithMockUser(roles = "SUPER_ADMIN")
//    @DisplayName("Throw exception if city already exist")
//    void updateCityThrowException() throws Exception {
//        addProvince();
//        addCityDistrict();
//        updateThrow(3).andExpect(status().isAlreadyReported());
//    }

    private ResultActions addProvince() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions addCityDistrict() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addCity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CityOrDistrictRegisterDto("Tashkent sh",8)));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getCityList");
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete("/api/v1/deleteCity/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/getCity/" + id);
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions update(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                put("/api/v1/updateCity/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CityOrDistrictRegisterDto("Qarshi sh",8)));
        return mockMvc.perform(requestBuilder);
    }
//    private ResultActions updateThrow(int id) throws Exception {
//        final MockHttpServletRequestBuilder requestBuilder =
//                put("/api/v1/updateProvince/"+id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
//        return mockMvc.perform(requestBuilder);
//    }
}