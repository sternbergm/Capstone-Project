package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;

import htd.project.models.Contractor;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContractorControllerTest {


    @MockBean
    ObjectRepository<Contractor> repository;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    @Autowired
    MockMvc mvc;

    Contractor contractor;

    ObjectMapper jsonMapper;

    String token;



    @BeforeEach
    void setUp() throws Exception {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        contractor = new Contractor(1, "John", "Doe",LocalDate.of(1990,01,01), "123 way st", "john@doe.com", BigDecimal.valueOf(3000000,2), true);

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
        when(repository.readAll()).thenReturn(List.of(contractor));

        var request = get("/contractor");

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        when(repository.readById(1)).thenReturn(contractor);

        var request = get("/contractor/1");

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void add() throws Exception{
        when(repository.create(contractor)).thenReturn(contractor);
        when(repository.readAll()).thenReturn(new ArrayList<>());

        String moduleJson = jsonMapper.writeValueAsString(contractor);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = post("/contractor")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(moduleJson));

        when(repository.readAll()).thenReturn(List.of(contractor));

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(repository.update(contractor)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(contractor));

        String moduleJson = jsonMapper.writeValueAsString(contractor);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = put("/contractor/1")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());
        mvc.perform(request)
                .andExpect(status().isBadRequest());

        when(repository.readAll()).thenReturn(List.of(contractor));

        request = put("/contractor/2")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void testDelete() throws Exception {

        when(repository.delete(1)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(contractor));

        when(CCMRepository.readByContractor(1)).thenReturn(new ArrayList<>());

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = delete("/contractor/1")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }
}