package htd.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ModuleControllerTest {

    @MockBean
    ObjectRepository<Module> repository;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    @Autowired
    MockMvc mvc;

    Module module;

    ObjectMapper jsonMapper;

    String token;



    @BeforeEach
    void setUp() throws Exception {
        jsonMapper = new ObjectMapper();
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        module = new Module(1, "Spring", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);

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
        when(repository.readAll()).thenReturn(List.of(module));

        var request = get("/module");

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        when(repository.readById(1)).thenReturn(module);

        var request = get("/module/1");

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void add() throws Exception{
        when(repository.create(module)).thenReturn(module);
        when(repository.readAll()).thenReturn(new ArrayList<>());

        String moduleJson = jsonMapper.writeValueAsString(module);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = post("/module")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json(moduleJson));

        when(repository.readAll()).thenReturn(List.of(module));

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        when(repository.update(module)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(module));

        String moduleJson = jsonMapper.writeValueAsString(module);

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = put("/module/1")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isNoContent());

        when(repository.readAll()).thenReturn(new ArrayList<>());
        mvc.perform(request)
                .andExpect(status().isBadRequest());

        when(repository.readAll()).thenReturn(List.of(module));

        request = put("/module/2")
                .headers(header)
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson);

        mvc.perform(request)
                .andExpect(status().isConflict());
    }

    @Test
    void testDelete() throws Exception {

        when(repository.delete(1)).thenReturn(true);
        when(repository.readAll()).thenReturn(List.of(module));
        when(CCMRepository.readByModule(1)).thenReturn(new ArrayList<>());

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+token);

        var request = delete("/module/1")
                .headers(header);

        mvc.perform(request)
                .andExpect(status().isNoContent());
    }
}