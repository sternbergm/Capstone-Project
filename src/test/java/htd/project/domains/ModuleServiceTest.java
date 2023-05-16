package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ModuleServiceTest {

    @Autowired
    ModuleService service;

    @MockBean
    ObjectRepository<Module> moduleRepository;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    Module module;

    @BeforeEach
    void setUp() {
        module = new Module(1, "Spring", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);

    }

    @Test
    void create() {
        when(moduleRepository.create(module)).thenReturn(module);

        Result<Module> result = service.create(module);

        assertTrue(result.isSuccessful());

        assertEquals(module, result.getPayload());

        module.setTopic(null);
        result = service.create(module);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        when(moduleRepository.readAll()).thenReturn(List.of(module));

        result = service.create(module);
        assertFalse(result.isSuccessful());

    }

    @Test
    void update() {
        when(moduleRepository.update(module)).thenReturn(true);

        Result<Module> result = service.update(module);

        assertTrue(result.isSuccessful());

        assertEquals(module, result.getPayload());

        when(moduleRepository.update(module)).thenReturn(false);

        result = service.update(module);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {

        when(moduleRepository.delete(1)).thenReturn(true);
        when(moduleRepository.readAll()).thenReturn(List.of(module));
        when(CCMRepository.readByModule(1)).thenReturn(new ArrayList<>());


        Result<Void> result = service.delete(1);

        assertTrue(result.isSuccessful());

        result = service.delete(500);

        assertFalse(result.isSuccessful());

        when(moduleRepository.delete(1)).thenReturn(false);

        result = service.delete(1);

        assertFalse(result.isSuccessful());

        when(CCMRepository.readByModule(1)).thenReturn(List.of(new ContractorCohortModule()));

        result = service.delete(1);

        assertFalse(result.isSuccessful());

    }
}