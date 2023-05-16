package htd.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CohortTest {

    Validator validator;
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailAll() {
        Cohort cohort = new Cohort();

        Set<ConstraintViolation<Cohort>> errors = validator.validate(cohort);

        assertEquals(6, errors.size());
    }

    @Test
    void shouldPassAll() {
        Cohort cohort = new Cohort(1, LocalDate.now(), LocalDate.now(), new Client(), new Instructor(), new ArrayList<>(), new ArrayList<>());

        Set<ConstraintViolation<Cohort>> errors = validator.validate(cohort);

        assertEquals(0, errors.size());
    }
}