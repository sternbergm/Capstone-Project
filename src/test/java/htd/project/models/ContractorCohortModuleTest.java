package htd.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ContractorCohortModuleTest {

    Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailGrade() {
        ContractorCohortModule ccm = new ContractorCohortModule();

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        assertEquals(1, errors.size());
    }

    @Test
    void shouldFailContractor() {
        ContractorCohortModule ccm = new ContractorCohortModule(-1,1,1, BigDecimal.valueOf(100));

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        assertEquals(1, errors.size());
    }

    @Test
    void shouldFailCohort() {
        ContractorCohortModule ccm = new ContractorCohortModule(1,-1,1, BigDecimal.valueOf(100));

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        assertEquals(1, errors.size());
    }

    @Test
    void shouldFailModule() {
        ContractorCohortModule ccm = new ContractorCohortModule(1,1,-1, BigDecimal.valueOf(100));

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        assertEquals(1, errors.size());
    }

    @Test
    void shouldFailGradeBounds() {
        ContractorCohortModule ccm = new ContractorCohortModule(1,1,1, BigDecimal.valueOf(110));

        Set<ConstraintViolation<ContractorCohortModule>> errors = validator.validate(ccm);

        assertEquals(1, errors.size());

        ccm.setGrade(BigDecimal.valueOf(-1));

        errors = validator.validate(ccm);

        assertEquals(1, errors.size());
    }
}