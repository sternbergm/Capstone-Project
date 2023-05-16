package htd.project.domains;

import htd.project.data.ObjectRepository;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ModuleServiceTest {

    @Autowired
    ModuleService service;

    @MockBean
    ObjectRepository<Module> moduleRepository;

    Module module;

    @BeforeEach
    void setUp() {
        module = new Module(1, "Spring", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);

    }

    @Test
    void create() {
        when(moduleRepository.create(module)).thenReturn(module);


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}