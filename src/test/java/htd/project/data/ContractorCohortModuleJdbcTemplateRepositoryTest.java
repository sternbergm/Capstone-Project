package htd.project.data;

import htd.project.models.ContractorCohortModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ContractorCohortModuleJdbcTemplateRepositoryTest {

    @Autowired
    ContractorCohortModuleRepository repository;

    @Autowired
    KnownGoodState knownGoodState;


    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void findAll() {
    }

    @Test
    void findByContractor() {
    }

    @Test
    void findByCohort() {
    }

    @Test
    void findByModule() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByIds() {
    }
}