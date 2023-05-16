package htd.project.data;

import htd.project.models.Cohort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}