package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.domains.ContractorCohortModuleService;
import htd.project.models.Cohort;
import htd.project.models.Contractor;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class ContractorCohortModuleControllerTest {

    @MockBean
    ContractorCohortModuleRepository repository;

    @Autowired
    ContractorCohortModuleService service;

    @MockBean
    ObjectRepository<Contractor> contractorRepository;

    @MockBean
    ObjectRepository<Cohort> cohortRepository;

    @MockBean
    ObjectRepository<Module> moduleRepository;

    @Autowired
    MockMvc mvc;

    ContractorCohortModule ccm;

    ObjectMapper jsonMapper;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ccm = new ContractorCohortModule(1, 1, 1, BigDecimal.TEN);

        Map<String, String> user = new HashMap<>();
        user.put("username", "john@smith.com");
        user.put("password", "P@ssw0rd!");

        String jsonUser = jsonMapper.writeValueAsString(user);

        var request = post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jsonUser);
        HashMap<String, String> map = jsonMapper.readValue(mvc.perform(request).andReturn().getResponse().getContentAsString(), HashMap.class);
        token = map.get("jwt_token");
    }

    @Test
    void findAll() throws Exception {
        when(repository.readAll()).thenReturn(List.of(ccm));

        var request = get("/grade");

        mvc.perform(request)
                .andExpect(status().isOk());

    }

    @Test
    void add() throws Exception {
        when(repository.create(ccm)).thenReturn(ccm);
        when(contractorRepository.readById(1)).thenReturn(new Contractor());
        when(cohortRepository.readById(1)).thenReturn(new Cohort());
        when(moduleRepository.readById(1)).thenReturn(new Module());

        String jsonCcm = jsonMapper.writeValueAsString(ccm);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = post("/grade")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCcm);

        mvc.perform(request)
                .andExpect(status().isCreated());


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }


}