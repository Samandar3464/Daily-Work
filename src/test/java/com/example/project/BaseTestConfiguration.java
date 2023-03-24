package com.example.project;

import com.example.project.repository.Address.CityOrDistrictRepository;
import com.example.project.repository.Address.ProvinceRepository;
import com.example.project.repository.PersonRepository;
import com.example.project.repository.WorkCategoryRepository;
import com.example.project.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class BaseTestConfiguration {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ProvinceRepository provinceRepository;
    @Autowired
    protected CityOrDistrictRepository cityOrDistrictRepository;
    @Autowired
    protected WorkCategoryRepository workCategoryRepository;
    @Autowired
    protected WorkRepository workRepository;
    @Autowired
    protected PersonRepository personRepository;

    protected static final PostgreSQLContainer<?> postgres;
    private static final String IMAGE_NAME = "postgres:latest";


    static {
        postgres = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse(IMAGE_NAME))
                .withInitScript("sql/table-init.sql")
                .withExposedPorts(5432);
        postgres.withReuse(true);
        Startables.deepStart(postgres).join();
    }

    @DynamicPropertySource
    static void setUpPostgresConnectionProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.database", postgres::getDatabaseName);
    }
}
