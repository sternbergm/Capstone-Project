package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Client;
import htd.project.models.Cohort;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @MockBean
    ObjectRepository<Client> repository;

    @MockBean
    ObjectRepository<Cohort> cohortRepository;

    @Autowired
    MockMvc mvc;

    Client client;

    ObjectMapper jsonMapper;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        client = new Client(1, "Main bank", "One Main st", 1000, "info@mainbank.com");

        Map<String, String> user = new HashMap<>();
        user.put("username", "john@smith.com");
        user.put("password", "P@ssw0rd!");

        String jsonUser = jsonMapper.writeValueAsString(user);

        var request = post("/authenticate").contentType(MediaType.APPLICATION_JSON).content(jsonUser);
        HashMap<String, String> map = jsonMapper.readValue(mvc.perform(request).andReturn().getResponse().getContentAsString(), HashMap.class);
        token = map.get("jwt_token");
    }

    @Test
    void add() throws Exception {
        when(repository.create(client)).thenReturn(client);
        when(repository.readAll()).thenReturn(new ArrayList<>());

        String jsonClient = jsonMapper.writeValueAsString(client);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = post("/client")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonClient);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonClient));

        when(repository.readAll()).thenReturn(List.of(client));

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(repository.update(client)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(client));

        String jsonClient = jsonMapper.writeValueAsString(client);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = put("/client/1")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonClient);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        request = put("/client/15")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonClient);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void testDelete() throws Exception {
        when(repository.delete(client.getClientId())).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(client));
        when(cohortRepository.readAll()).thenReturn(new ArrayList<>());

        String jsonClient = jsonMapper.writeValueAsString(client);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = delete("/client/1")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());

        mvc.perform(request)
                .andExpect(status().isBadRequest());

        request = delete("/client/15")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}