package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InstructorControllerTest {

    @MockBean
    ObjectRepository<Instructor> repository;

    @MockBean
    ObjectRepository<Cohort> cohortRepository;

    @Autowired
    MockMvc mvc;

    Instructor instructor;

    ObjectMapper jsonMapper;

    String token;

    @BeforeEach
    void setUp() throws Exception{
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        instructor = new Instructor(1, "James", "Bond", 30, "espionage", BigDecimal.valueOf(3000000,2));

        Map<String, String> user = new HashMap<>();
        user.put("username", "john@smith.com");
        user.put("password", "P@ssw0rd!");

        String jsonUser = jsonMapper.writeValueAsString(user);

        var request = post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jsonUser);
        HashMap<String, String> map = jsonMapper.readValue(mvc.perform(request).andReturn().getResponse().getContentAsString(), HashMap.class);
        token = map.get("jwt_token");
    }

    @Test
    void add() throws Exception{
        when(repository.create(instructor)).thenReturn(instructor);
        when(repository.readAll()).thenReturn(new ArrayList<>());

        String instructorJson = jsonMapper.writeValueAsString(instructor);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = post("/instructor")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(instructorJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(instructorJson));

        when(repository.readAll()).thenReturn(List.of(instructor));

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(repository.update(instructor)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(instructor));

        String instructorJson = jsonMapper.writeValueAsString(instructor);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = put("/instructor/1")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(instructorJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        request = put("/instructor/15")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(instructorJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void testDelete() throws Exception {
        when(repository.delete(instructor.getInstructorId())).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(instructor));
        when(cohortRepository.readAll()).thenReturn(new ArrayList<>());

        String instructorJson = jsonMapper.writeValueAsString(instructor);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = delete("/instructor/1")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        request = delete("/instructor/15")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}