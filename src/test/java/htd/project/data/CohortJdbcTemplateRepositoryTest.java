package htd.project.data;

import htd.project.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CohortJdbcTemplateRepositoryTest {

    @Autowired
    ObjectRepository<Cohort> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Cohort cohort1;

    Cohort cohort2;

    List<Cohort> cohorts;

    @BeforeEach
    void setUp() {
        knownGoodState.set();

        cohort1 = new Cohort(1, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 3, 1), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort2 = new Cohort(2, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 8, 1), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());

        cohorts = new ArrayList<>();
        cohorts.add(cohort1);
        cohorts.add(cohort2);

    }

    @Test
    void readAll() {
        List<Cohort> result = repository.readAll();

        assertEquals(cohorts, result);
    }

    @Test
    void readById() {
        Cohort result = repository.readById(1);

        assertEquals(cohort1, result);

        result = repository.readById(500);

        assertNull(result);
    }

    @Test
    void create() {
        Cohort cohort = new Cohort(3, LocalDate.of(2023, 10, 1), LocalDate.of(2023, 12, 1), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort.getClient().setClientId(1);
        cohort.getInstructor().setInstructorId(1);

        Cohort result = repository.create(cohort);

        assertEquals(cohort, result);
        assertEquals(cohorts.size()+1, repository.readAll().size());
    }

    @Test
    void update() {
        Cohort cohort = new Cohort(2, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 8, 1), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort.getClient().setClientId(1);
        cohort.getInstructor().setInstructorId(1);

        assertTrue(repository.update(cohort));

        assertEquals(cohort, repository.readById(2));

        cohort.setCohortId(500);

        assertFalse(repository.update(cohort));

    }

    @Test
    void delete() {

        Cohort cohort = new Cohort(3, LocalDate.of(2023, 10, 1), LocalDate.of(2023, 12, 1), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());
        cohort.getClient().setClientId(1);
        cohort.getInstructor().setInstructorId(1);

        Cohort result = repository.create(cohort);

        assertTrue(repository.delete(3));
        assertEquals(cohorts.size(), repository.readAll().size());

        assertFalse(repository.delete(500));
    }
}