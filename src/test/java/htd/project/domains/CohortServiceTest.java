package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.*;
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
class CohortServiceTest {

    @Autowired
    CohortService service;

    @MockBean
    ObjectRepository<Cohort> cohortRepository;

    @MockBean
    ContractorCohortModuleRepository CCMRepository;

    Cohort cohort;

    @BeforeEach
    void setUp() {
        cohort = new Cohort(1, LocalDate.of(2023, 03, 01), LocalDate.of(2023, 05, 01), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort.getClient().setClientId(1);
        cohort.getInstructor().setInstructorId(1);
    }

    @Test
    void create() {
        when(cohortRepository.create(cohort)).thenReturn(cohort);

        Result<Cohort> result = service.create(cohort);

        assertTrue(result.isSuccessful());
        assertEquals(cohort, result.getPayload());

        cohort.setStartDate(null);

        result = service.create(cohort);

        assertFalse(result.isSuccessful());

        cohort.setStartDate(LocalDate.now());

        when(cohortRepository.readAll()).thenReturn(List.of(cohort));

        result = service.create(cohort);

        assertFalse(result.isSuccessful());

    }

    @Test
    void update() {
        when(cohortRepository.update(cohort)).thenReturn(true);
        when(cohortRepository.readAll()).thenReturn(List.of(cohort));
        Result<Cohort> result = service.update(cohort);

        assertTrue(result.isSuccessful());
        assertEquals(cohort, result.getPayload());

        cohort.setStartDate(null);

        result = service.update(cohort);

        assertFalse(result.isSuccessful());

        cohort.setStartDate(LocalDate.now());

        when(cohortRepository.update(cohort)).thenReturn(false);

        result = service.update(cohort);

        assertFalse(result.isSuccessful());

        when(cohortRepository.readAll()).thenReturn(new ArrayList<>());
        result = service.update(cohort);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {
        when(cohortRepository.delete(1)).thenReturn(true);
        when(cohortRepository.readAll()).thenReturn(List.of(cohort));
        when(CCMRepository.readByCohort(1)).thenReturn(new ArrayList<>());

        Result<Void> result = service.delete(1);

        assertTrue(result.isSuccessful());

        when(cohortRepository.delete(1)).thenReturn(false);
        result = service.delete(1);

        assertFalse(result.isSuccessful());

        when(cohortRepository.delete(1)).thenReturn(true);
        when(CCMRepository.readByCohort(1)).thenReturn(List.of(new ContractorCohortModule()));
        result = service.delete(1);

        assertFalse(result.isSuccessful());

        when(CCMRepository.readByCohort(1)).thenReturn(new ArrayList<>());
        when(cohortRepository.readAll()).thenReturn(new ArrayList<>());
        result = service.delete(1);

        assertFalse(result.isSuccessful());


    }
}