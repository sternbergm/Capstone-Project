package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Client;
import htd.project.models.Cohort;
import htd.project.models.Instructor;
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

        when(cohortRepository.readAll()).thenReturn(List.of(cohort));

        result = service.create(cohort);

        assertFalse(result.isSuccessful());

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}