package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Contractor;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ContractorServiceTest {

    @Autowired
    ContractorService service;

    @MockBean
    ObjectRepository<Contractor> contractorRepository;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    Contractor contractor;

    @BeforeEach
    void setUp() {
        contractor= new Contractor(1, "John", "Doe",LocalDate.of(1990,01,01), "123 way st", "john@doe.com", BigDecimal.valueOf(3000000,2), true);

    }

    @Test
    void create() {
        when(contractorRepository.create(contractor)).thenReturn(contractor);

        Result<Contractor> result = service.create(contractor);

        assertTrue(result.isSuccessful());

        assertEquals(contractor, result.getPayload());

        contractor.setFirstName(null);
        result = service.create(contractor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        contractor.setLastName(null);
        result = service.create(contractor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        contractor.setAddress(null);
        result = service.create(contractor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        contractor.setEmail(null);
        result = service.create(contractor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        when(contractorRepository.readAll()).thenReturn(List.of(contractor));

        result = service.create(contractor);
        assertFalse(result.isSuccessful());

    }

    @Test
    void update() {
        when(contractorRepository.update(contractor)).thenReturn(true);
        when(contractorRepository.readAll()).thenReturn(List.of(contractor));
        Result<Contractor> result = service.update(contractor);

        assertTrue(result.isSuccessful());

        assertEquals(contractor, result.getPayload());

        when(contractorRepository.update(contractor)).thenReturn(false);

        result = service.update(contractor);

        assertFalse(result.isSuccessful());

        when(contractorRepository.readAll()).thenReturn(new ArrayList<>());

        result = service.update(contractor);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {

        when(contractorRepository.delete(1)).thenReturn(true);
        when(contractorRepository.readAll()).thenReturn(List.of(contractor));
        when(CCMRepository.readByContractor(1)).thenReturn(new ArrayList<>());

        Result<Void> result = service.delete(1);

        assertTrue(result.isSuccessful());

        result = service.delete(500);

        assertFalse(result.isSuccessful());

        when(contractorRepository.delete(1)).thenReturn(false);

        result = service.delete(1);

        assertFalse(result.isSuccessful());


    }
}
