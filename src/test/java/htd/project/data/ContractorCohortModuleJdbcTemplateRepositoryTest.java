package htd.project.data;

import htd.project.models.ContractorCohortModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

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
        List<ContractorCohortModule> result = repository.readAll();

        assertEquals(4, result.size());
    }

    @Test
    void findByContractor() {
        List<ContractorCohortModule> result = repository.readByContractor(1);

        assertEquals(2, result.size());

        result = repository.readByContractor(500);

        assertEquals(0, result.size());
    }

    @Test
    void findByCohort() {
        List<ContractorCohortModule> result = repository.readByCohort(1);

        assertEquals(2, result.size());

        result = repository.readByCohort(500);

        assertEquals(0, result.size());
    }

    @Test
    void findByModule() {
        List<ContractorCohortModule> result = repository.readByModule(1);

        assertEquals(2, result.size());

        result = repository.readByModule(500);

        assertEquals(0, result.size());
    }

    @Test
    void create() {
        ContractorCohortModule ccm = new ContractorCohortModule(1, 2, 1, BigDecimal.valueOf(75));

        ContractorCohortModule result  = repository.create(ccm);

        assertEquals(ccm, result);
        assertEquals(5, repository.readAll().size());
    }

    @Test
    void update() {
        ContractorCohortModule ccm = new ContractorCohortModule(1, 1, 1, BigDecimal.valueOf(75));

        assertTrue(repository.update(ccm));

        assertEquals(4, repository.readAll().size());

        ccm.setCohortId(500);
        assertFalse(repository.update(ccm));
    }

    @Test
    void deleteByIds() {

        assertTrue(repository.deleteByIds(1,1,1));

        assertEquals(3, repository.readAll().size());

        assertFalse(repository.deleteByIds(300,1,1));
        assertFalse(repository.deleteByIds(1,200,1));
        assertFalse(repository.deleteByIds(1,1,200));
    }
}