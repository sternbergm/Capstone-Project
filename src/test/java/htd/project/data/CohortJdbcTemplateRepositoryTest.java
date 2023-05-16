package htd.project.data;

import htd.project.models.Cohort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CohortJdbcTemplateRepositoryTest {

    @Autowired
    ObjectRepository<Cohort> repository;

    @Autowired
    KnownGoodState knownGoodState;

    Cohort cohort1;

    Cohort cohort2;



    @BeforeEach
    void setUp() {
    }

    @Test
    void readAll() {
    }

    @Test
    void readById() {
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