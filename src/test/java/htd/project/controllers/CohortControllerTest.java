package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Client;
import htd.project.models.Cohort;
import htd.project.models.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CohortControllerTest {

    @MockBean
    ObjectRepository<Cohort> repository;

    @Autowired
    MockMvc mvc;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    ObjectMapper jsonMapper;

    Cohort cohort;

    String token;


    @BeforeEach
    void setUp() throws Exception {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        cohort = new Cohort(1, LocalDate.of(2023, 03, 01), LocalDate.of(2023, 05, 01), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort.getClient().setClientId(1);
        cohort.getInstructor().setInstructorId(1);

        Map<String, String> user = new HashMap<>();
        user.put("username", "john@smith.com");
        user.put("password", "P@ssw0rd!");

        String jsonUser = jsonMapper.writeValueAsString(user);

        var request = post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jsonUser);
        HashMap<String, String> map = jsonMapper.readValue(mvc.perform(request).andReturn().getResponse().getContentAsString(), HashMap.class);
        token = map.get("jwt_token");

    }

    @Test
    void findById() throws Exception {
        when(repository.readById(1)).thenReturn(cohort);

        var request = get("/cohort/1");

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void add() throws Exception {
        when(repository.create(cohort)).thenReturn(cohort);
        when(repository.readAll()).thenReturn(new ArrayList<>());

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        String cohortJson = jsonMapper.writeValueAsString(cohort);

        var request = post("/cohort")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cohortJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(cohortJson));

        when(repository.readAll()).thenReturn(List.of(cohort));

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        when(repository.readAll()).thenReturn(new ArrayList<>());

        cohort.setStartDate(null);
        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(repository.update(cohort)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(cohort));

        String cohortJson = jsonMapper.writeValueAsString(cohort);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = put("/cohort/1")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(cohortJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete() throws Exception {
        when(repository.delete(1)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(cohort));
        when(CCMRepository.readByCohort(1)).thenReturn(new ArrayList<>());

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = delete("/cohort/1")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }
}