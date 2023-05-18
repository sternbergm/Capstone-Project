package htd.project.domains;

import htd.project.data.ContractorCohortModuleRepository;
import htd.project.data.ObjectRepository;
import htd.project.models.Cohort;
import htd.project.models.ContractorCohortModule;
import htd.project.models.Instructor;
import htd.project.models.Module;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class InstructorServiceTest {

    @Autowired
    InstructorService service;

    @MockBean
    ObjectRepository<Instructor> instructorRepository;
    @MockBean
    ObjectRepository<Cohort> cohortRepository;


    Instructor instructor;

    @BeforeEach
    void setUp() {
        instructor = new Instructor(1, "James", "Bond", 30, "espionage", BigDecimal.valueOf(3000000,2));

    }

    @Test
    void create() {
        when(instructorRepository.create(instructor)).thenReturn(instructor);

        Result<Instructor> result = service.create(instructor);

        assertTrue(result.isSuccessful());

        assertEquals(instructor, result.getPayload());

        instructor.setFirstName(null);
        result = service.create(instructor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());
        instructor.setLastname(null);
        result = service.create(instructor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        instructor.setExpertise(null);
        result = service.create(instructor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        instructor.setSalary(null);
        result = service.create(instructor);
        assertFalse(result.isSuccessful());
        assertNull(result.getPayload());

        when(instructorRepository.readAll()).thenReturn(List.of(instructor));

        result = service.create(instructor);
        assertFalse(result.isSuccessful());

    }

    @Test
    void update() {
        when(instructorRepository.update(instructor)).thenReturn(true);
        when(instructorRepository.readAll()).thenReturn(List.of(instructor));

        Result<Instructor> result = service.update(instructor);
        assertTrue(result.isSuccessful());

        assertEquals(instructor, result.getPayload());

        when(instructorRepository.update(instructor)).thenReturn(false);

        result = service.update(instructor);

        assertFalse(result.isSuccessful());

        when(instructorRepository.readAll()).thenReturn(new ArrayList<>());

        result = service.update(instructor);

        assertFalse(result.isSuccessful());
    }

    @Test
    void delete() {

        when(instructorRepository.delete(1)).thenReturn(true);
        when(instructorRepository.readAll()).thenReturn(List.of(instructor));
        when(cohortRepository.readAll()).thenReturn(new ArrayList<>());

        Result<Void> result = service.delete(1);

        assertTrue(result.isSuccessful());

        result = service.delete(500);

        assertFalse(result.isSuccessful());

        when(instructorRepository.delete(1)).thenReturn(false);

        result = service.delete(1);

        assertFalse(result.isSuccessful());



    }

}
