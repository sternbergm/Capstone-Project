package htd.project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    Module module;

    Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validatorShouldPass() {
        Module module = new Module(1, "Spring", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);

        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(0, errors.size());
    }

    @Test
    void validatorShouldFail() {
        Module module = null;

        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(5, errors.size());
    }

    @Test
    void validatorShouldFailTopic() {
        Module module = new Module(1, null, LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, 5);

        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(1, errors.size());

        module.setTopic("");

        errors = validator.validate(module);

        assertEquals(1, errors.size());

        module.setTopic("topictopictopictopictopictopictopictopictopictopictopictopictopictopictopictopictopictopic");

        errors = validator.validate(module);

        assertEquals(1, errors.size());
    }

    @Test
    void validatorShouldFailDate() {
        Module module = new Module(1, "Topic", null, LocalDate.of(2023,04,07), 5, 5);
        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(1, errors.size());

        module = new Module(1, "Topic",  LocalDate.of(2023,04,07), null, 5, 5);

        errors = validator.validate(module);

        assertEquals(1, errors.size());

    }

    @Test
    void validatorShouldFailExerciseAmount() {
        Module module = new Module(1, "Topic", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), -5, 5);
        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(1, errors.size());

        module.setExerciseAmount(55);

       errors = validator.validate(module);

        assertEquals(1, errors.size());
    }

    @Test
    void validatorShouldFailLessonAmount() {
        Module module = new Module(1, "Topic", LocalDate.of(2023, 04, 01), LocalDate.of(2023,04,07), 5, -5);
        Set<ConstraintViolation<Module>> errors = validator.validate(module);

        assertEquals(1, errors.size());

        module.setLessonAmount(55);

        errors = validator.validate(module);

        assertEquals(1, errors.size());
    }
}