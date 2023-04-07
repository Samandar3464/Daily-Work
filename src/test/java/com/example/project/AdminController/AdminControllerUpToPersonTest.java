package com.example.project.AdminController;

import com.example.project.BaseTestConfiguration;
import com.example.project.entity.ENUM.Role;
import com.example.project.model.PersonRegisterDto;
import com.example.project.model.RoleChange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminControllerUpToPersonTest extends BaseTestConfiguration {
    @AfterEach
    void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getUsersList() throws Exception {
        add();
        getPersonList().andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void getPersonById() throws Exception {
        add();
        getById(2).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void deleteUser() throws Exception {
        add();
        deleteById(4).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "SUPER_ADMIN")
    void changePersonRole() throws Exception {
        add();
        changeRole().andExpect(status().isOk());
    }
    private ResultActions add() throws Exception {
        final MockHttpServletRequestBuilder request =
                post("/api/v1/person/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new PersonRegisterDto("123777777", "User", "123456")));
        return mockMvc.perform(request);
    }
    private ResultActions getPersonList() throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/admin/person/personList");
        return mockMvc.perform(request);
    }
    private ResultActions getById(int id) throws Exception {
        final MockHttpServletRequestBuilder request =
                get("/api/v1/admin/person/getPersonById/"+id);
        return mockMvc.perform(request);
    }
    private ResultActions deleteById(int id) throws Exception {
        final MockHttpServletRequestBuilder request =
                delete("/api/v1/admin/person/deletePerson/"+id);
        return mockMvc.perform(request);
    }
    private ResultActions changeRole() throws Exception {
        final MockHttpServletRequestBuilder request =
                put("/api/v1/admin/person/changePersonRole")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new RoleChange(Role.ADMIN,3)));
        return mockMvc.perform(request);
    }
}