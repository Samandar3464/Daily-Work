package com.example.project.AdminController;

import com.example.project.BaseTestConfiguration;
import com.example.project.model.WorkCategoryRegisterDto;
import com.example.project.model.WorkRegisterDto;
import com.example.project.model.address.CityOrDistrictRegisterDto;
import com.example.project.model.address.ProvinceRegisterDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@SpringBootTest
class AdminControllerUpToWorkTest extends BaseTestConfiguration {

    @AfterEach
    void tearDown() {
        workRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getWorkList() throws Exception {
        addProvince();
        addCityDistrict();
        addCategory();
        add();
        getList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteWorkById() throws Exception {
        add();
        callDelete(2);
    }
    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteWorkByIdThrow() throws Exception {
        callDelete(999);
    }
    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/work/addWork")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createWork()));
        return mockMvc.perform(requestBuilder);
    }

    private ResultActions getList() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                get("/api/v1/admin/work/getWorkList");
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions callDelete(int id) throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                delete("/api/v1/admin/work/deleteById/" + id);
        return mockMvc.perform(requestBuilder);
    }

    private WorkRegisterDto createWork(){
        return WorkRegisterDto.builder()
                .workTitle("Remont")
                .workDescription("Remont")
                .startPrice(100.0)
                .endPrice(1000.0)
                .workCategoryId(1)
                .provinceId(1)
                .cityOrDistrictId(1)
                .village("qarshi")
                .build();
    }
    private ResultActions addCityDistrict() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addCity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CityOrDistrictRegisterDto("Tashkent sh",1)));
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions addCategory() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/category/admin/addCategory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new WorkCategoryRegisterDto("Qurulish")));
        return mockMvc.perform(requestBuilder);
    }
    private ResultActions addProvince() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder =
                post("/api/v1/addProvince")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ProvinceRegisterDto("Tashkent")));
        return mockMvc.perform(requestBuilder);
    }
}